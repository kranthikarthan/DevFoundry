# @devportal/sdk

## Usage
```ts
import { IntegrationClient } from '@devportal/sdk';

const client = new IntegrationClient('http://localhost:3000');
const { integrations } = await client.listIntegrations();
console.log(integrations);
```
