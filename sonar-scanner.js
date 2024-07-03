const scanner = require("sonarqube-scanner");
scanner(
  {
    serverUrl: process.env.SONAR_HOST_URL,
    token: process.env.SONAR_TOKEN,
    options: {
      "sonar.sources": "./frontend/src",
      "sonar.exclusions": "**/*.test.tsx",
      "sonar.tests": "/frontend/cypress/e2e",
      "sonar.test.inclusions": "**/*.test.tsx,**/*.test.ts",
      "sonar.javascript.lcov.reportPaths": "coverage/lcov.info",
      "sonar.testExecutionReportPaths": "test-report.xml",
    },
  },
  () => process.exit()
);