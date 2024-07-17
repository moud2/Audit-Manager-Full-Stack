const fs = require('fs');
const nycrcContent = {
  include: ["instrumented/*.js"],
  exclude: ["cypress/e2e/*.js"],
  reporter: ["lcov", "text"]
};
fs.writeFileSync('.nycrc', JSON.stringify(nycrcContent, null, 2));
console.log('.nycrc file created');
