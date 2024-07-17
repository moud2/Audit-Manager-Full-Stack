const fs = require('fs');
const path = require('path');

// Pfad zur Cypress-Konfigurationsdatei
const configPath = path.join(__dirname, 'cypress.config.cjs');

// Neuer Inhalt für die Cypress-Konfigurationsdatei
const newConfig = `
const { defineConfig } = require('cypress');
const codeCoverageTask = require('@cypress/code-coverage/task');

module.exports = defineConfig({
  // setupNodeEvents can be defined in either
  // the e2e or component configuration
  e2e: {
    specPattern: 'instrumented/**/*.js',
    setupNodeEvents(on, config) {
      require('@cypress/code-coverage/task')(on, config);
      // include any other plugin code...

      // It's IMPORTANT to return the config object
      // with any changed environment variables
      return config;
    },
  },
});
`;

// Funktion zur Aktualisierung der Cypress-Konfiguration
function updateCypressConfig() {
  // Schreibe den neuen Inhalt in die Konfigurationsdatei
  fs.writeFile(configPath, newConfig.trim(), 'utf8', (err) => {
    if (err) {
      console.error('Fehler beim Schreiben der Datei:', err);
      return;
    }
    console.log('Cypress-Konfiguration erfolgreich aktualisiert.');
  });
}

// Aktualisiere die Cypress-Konfiguration
updateCypressConfig();
