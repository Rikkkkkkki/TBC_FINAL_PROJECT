import http from 'k6/http';
import { check, sleep } from 'k6';
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';

// Test Configuration
export let options = {
  stages: [
    { duration: '30s', target: 25 },   // Ramp-up: 0 to 25 users in 1 minute (gradual)
    { duration: '2m', target: 25 },   // Sustained load: 25 users for 2 minutes
    { duration: '30s', target: 0 },   // Ramp-down: 28 to 0 users
  ],
  
  // Performance thresholds
  thresholds: {
    http_req_duration: ['p(95)<3000', 'p(99)<5000'],      // P95 < 3s, P99 < 5s
    http_req_failed: ['rate<0.20'],                    // Error rate < 20%
    http_reqs: ['rate>8'],                             // At least 8 requests per second
    checks: ['rate>0.80'],                             // 80% of checks should pass
  },
};

// Basic headers
const headers = {
  'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
  'Accept-Language': 'ka,en;q=0.9',
  'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36',
  'Accept-Encoding': 'gzip, deflate, br',
  'Connection': 'keep-alive',
  'Cache-Control': 'no-cache',
};

// Test Scenario - Main function that each virtual user executes
export default function () {
  // Test 1: Load Offers Page
  let offersPageResponse = http.get('https://tbcbank.ge/ka/offers', {
    headers: headers,
    tags: { name: 'LoadOffersPage' },
  });
  
  // Validate offers page response
  let checksResult = check(offersPageResponse, {
    'Offers page status is 200 or 3xx': (r) => r.status === 200 || (r.status >= 300 && r.status < 400),
    'Offers page response time < 2s': (r) => r.timings.duration < 2000,
    'Offers page has content': (r) => r.body && r.body.length > 1000,
    'No server errors (5xx)': (r) => r.status < 500,
    'Response has HTML': (r) => r.body && r.body.includes('html'),
  });
  
  // Log failures for debugging
  if (!checksResult) {
    console.log(`Request failed - Status: ${offersPageResponse.status}, Body length: ${offersPageResponse.body ? offersPageResponse.body.length : 0}`);
  }
  
  // Simulate user reading the page
  sleep(Math.random() * 2 + 1);
  
  // Test 2: Additional request to test consistency
  let secondResponse = http.get('https://tbcbank.ge/ka/offers', {
    headers: headers,
    tags: { name: 'SecondRequest' },
  });
  
  check(secondResponse, {
    'Second request successful': (r) => r.status === 200 || (r.status >= 300 && r.status < 400),
    'Second request response time < 1.5s': (r) => r.timings.duration < 1500,
  });
  
  // Simulate user interaction delay
  sleep(1);
}

// Setup function
export function setup() {
  console.log('  Starting K6 Performance Test for TBC Bank Offers Page');
  console.log('  Test Configuration:');
  console.log('   - Ramp-up: 30 seconds to 25 users');
  console.log('   - Duration: 2 minutes at 25 users');
  console.log('   - Ramp-down: 30 seconds to 0 users');
  console.log('   - Target URL: https://tbcbank.ge/ka/offers');
  console.log('   - Test Type: Public page load testing');
  console.log('---------------------------------------------------');
  
  // Test initial connection
  let testResponse = http.get('https://tbcbank.ge/ka/offers');
  console.log(`Initial connection test - Status: ${testResponse.status}`);
  
  return testResponse.status;
}

// Teardown function - runs once after the test completes
export function teardown(data) {
  console.log('---------------------------------------------------');
  console.log(' K6 Performance Test Completed');
  console.log(' Check k6/K6Report.html for detailed results');
  console.log(` Initial connection status was: ${data}`);
}

// HTML Report Generation
export function handleSummary(data) {
  return {
    'k6/K6Report.html': htmlReport(data, { debug: false }),
    stdout: textSummary(data, { indent: ' ', enableColors: true }),
  };
}