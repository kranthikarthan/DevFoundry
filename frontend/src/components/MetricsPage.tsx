import React, { useState, useEffect } from 'react';
import {
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  Grid,
  Alert,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Chip,
} from '@mui/material';
import {
  Analytics,
  Speed,
  GetApp,
  Description,
  Refresh,
} from '@mui/icons-material';

import { metricsApi } from '../services/api';

interface MetricData {
  message: string;
  status: string;
}

const MetricsPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [lastAction, setLastAction] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const simulateAction = async (action: string, apiCall: () => Promise<MetricData>) => {
    try {
      setLoading(true);
      setError(null);

      const result = await apiCall();
      setLastAction(`${action}: ${result.message}`);
    } catch (err) {
      setError(`Failed to simulate ${action.toLowerCase()}`);
      console.error(`Error simulating ${action}:`, err);
    } finally {
      setLoading(false);
    }
  };

  const handleSimulateRequest = () => {
    simulateAction('Integration Request', metricsApi.simulateIntegrationRequest);
  };

  const handleSimulateDownload = () => {
    simulateAction('Template Download', metricsApi.simulateTemplateDownload);
  };

  const handleSimulateDocs = () => {
    simulateAction('Documentation View', metricsApi.simulateDocumentationView);
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Metrics & Monitoring
      </Typography>

      <Typography variant="body1" color="text.secondary" paragraph>
        This page provides access to custom metrics and allows you to simulate various events
        to test the monitoring capabilities of the platform.
      </Typography>

      <Grid container spacing={3}>
        <Grid item xs={12} md={8}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Available Metrics
              </Typography>

              <List>
                <ListItem>
                  <ListItemIcon><Analytics color="primary" /></ListItemIcon>
                  <ListItemText
                    primary="Integration Requests"
                    secondary="Total number of integration requests processed"
                  />
                </ListItem>
                <ListItem>
                  <ListItemIcon><GetApp color="primary" /></ListItemIcon>
                  <ListItemText
                    primary="Template Downloads"
                    secondary="Total number of template downloads"
                  />
                </ListItem>
                <ListItem>
                  <ListItemIcon><Description color="primary" /></ListItemIcon>
                  <ListItemText
                    primary="Documentation Views"
                    secondary="Total number of documentation page views"
                  />
                </ListItem>
                <ListItem>
                  <ListItemIcon><Speed color="primary" /></ListItemIcon>
                  <ListItemText
                    primary="Active Users"
                    secondary="Current number of active users on the platform"
                  />
                </ListItem>
              </List>

              <Alert severity="info" sx={{ mt: 2 }}>
                <Typography variant="body2">
                  <strong>Prometheus Endpoint:</strong> Available at /actuator/prometheus
                </Typography>
              </Alert>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Simulate Events
              </Typography>

              <Typography variant="body2" color="text.secondary" paragraph>
                Use these buttons to simulate events and generate metrics data for testing.
              </Typography>

              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <Button
                  variant="contained"
                  startIcon={<Analytics />}
                  onClick={handleSimulateRequest}
                  disabled={loading}
                  fullWidth
                >
                  Simulate Integration Request
                </Button>

                <Button
                  variant="contained"
                  startIcon={<GetApp />}
                  onClick={handleSimulateDownload}
                  disabled={loading}
                  fullWidth
                >
                  Simulate Template Download
                </Button>

                <Button
                  variant="contained"
                  startIcon={<Description />}
                  onClick={handleSimulateDocs}
                  disabled={loading}
                  fullWidth
                >
                  Simulate Documentation View
                </Button>
              </Box>

              {loading && (
                <Box sx={{ mt: 2 }}>
                  <Chip label="Processing..." color="primary" />
                </Box>
              )}

              {error && (
                <Alert severity="error" sx={{ mt: 2 }}>
                  {error}
                </Alert>
              )}

              {lastAction && (
                <Alert severity="success" sx={{ mt: 2 }}>
                  {lastAction}
                </Alert>
              )}
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      <Card sx={{ mt: 3 }}>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Service Health
          </Typography>

          <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
            <Chip label="Integration Service: Healthy" color="success" />
            <Chip label="Performance Service: Healthy" color="success" />
            <Chip label="Documentation Service: Healthy" color="success" />
            <Chip label="Metrics Service: Healthy" color="success" />
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
};

export default MetricsPage;