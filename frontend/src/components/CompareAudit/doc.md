# CompareAudit

# Props

# selectedAudit

- Type: `object`

The selected audit data to pass to the CompareAudits page.

## Methods

Handles navigation to the CompareAudits page with the selected audit data.

## Usage

```jsx
import { CompareAudit } from "./CompareAudit";

<CompareAudit selectedAudit={selectedAudit} />;
```

# AuditDropdown Component

## Description

The AuditDropdown component provides a dropdown for selecting an audit from a list of available audits. When an audit is selected, the callback function onAuditSelect is called.

## Props

### audits

- Type: `Array`

A list of audit objects to populate the dropdown.

### onAuditSelect

- Type: `Function`

Callback function triggered when an audit is selected.

## Methods

### handleSelection

Handles the audit selection and invokes the onAuditSelect callback with the selected audit.

## Usage

```jsx
import { AuditDropdown } from "./AuditDropdown";

const audits = [
  { id: 1, name: "Audit 1" },
  { id: 2, name: "Audit 2" },
];

const handleAuditSelect = (selectedAudit) => {
  console.log(selectedAudit);
};

<AuditDropdown audits={audits} onAuditSelect={handleAuditSelect} />;
```

# AuditComparisonCard

## Description

The AuditComparisonCard component displays detailed comparison data for a selected audit. It includes a progress bar for overall progress, circular progress indicators for category-wise progress, and a bar chart for points distribution.

## Props

### name

- Type: `string`

The name of the audit.

### progress

- Type: `number`

The overall progress of the audit.

### categories

- Type: `Array`

Array of category objects, each containing id, name, and progress.

### distribution

- Type: `Array`

Data for points distribution used in the bar chart.

## Usage

```jsx
import { AuditComparisonCard } from "./AuditComparisonCard";

const categories = [
  { id: 1, name: "Category 1", progress: 75 },
  { id: 2, name: "Category 2", progress: 50 },
];

const distribution = [
  { label: "Label 1", value: 30 },
  { label: "Label 2", value: 70 },
];

<AuditComparisonCard
  name="Audit Comparison"
  progress={80}
  categories={categories}
  distribution={distribution}
/>;
```

## Methods

Relies on props and child components for rendering.
