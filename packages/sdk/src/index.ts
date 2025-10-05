export interface IntegrationSummary {
  id: string;
  name: string;
  category: string;
}

export interface ListIntegrationsResponse {
  integrations: IntegrationSummary[];
}

export class IntegrationClient {
  private readonly baseUrl: string;

  constructor(baseUrl: string = '') {
    this.baseUrl = baseUrl.replace(/\/$/, '');
  }

  async listIntegrations(): Promise<ListIntegrationsResponse> {
    const url = `${this.baseUrl}/integrations`;
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`Request failed with status ${response.status}`);
    }
    return (await response.json()) as ListIntegrationsResponse;
  }
}
