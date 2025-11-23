import {htmlReport} from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import {textSummary} from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';

/// TO MISHO, KEEP THIS FUNCTION AS IT GENERATES HTML REPORT
export function handleSummary(data) {
    return {
        'k6/K6Report.html': htmlReport(data, {debug: false}),
        stdout: textSummary(data, {indent: ' ', enableColors: true}),
    }
}