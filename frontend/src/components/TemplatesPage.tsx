import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import {
  Typography,
  Grid,
  Card,
  CardContent,
  CardActions,
  Button,
  Box,
  Chip,
  Tabs,
  Tab,
  Rating,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Divider,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Alert,
} from '@mui/material';
import {
  ExpandMore,
  Code,
  Speed,
  Security,
  GetApp,
  Assessment,
  Schedule,
} from '@mui/icons-material';
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { tomorrow } from 'react-syntax-highlighter/dist/esm/styles/prism';

import { IntegrationTemplate, IntegrationType } from '../types';
import { integrationApi } from '../services/api';

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`template-tabpanel-${index}`}
      aria-labelledby={`template-tab-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
    </div>
  );
}

const TemplatesPage: React.FC = () => {
  const { type, id } = useParams<{ type?: string; id?: string }>();
  const [templates, setTemplates] = useState<IntegrationTemplate[]>([]);
  const [selectedTemplate, setSelectedTemplate] = useState<IntegrationTemplate | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [tabValue, setTabValue] = useState(0);

  useEffect(() => {
    loadTemplates();
  }, [type]);

  useEffect(() => {
    if (id && templates.length > 0) {
      const template = templates.find(t => t.id === id);
      if (template) {
        setSelectedTemplate(template);
      }
    }
  }, [id, templates]);

  const loadTemplates = async () => {
    try {
      setLoading(true);
      setError(null);

      let data: IntegrationTemplate[];
      if (type) {
        data = await integrationApi.getTemplatesByType(type as IntegrationType);
      } else {
        data = await integrationApi.getAllTemplates();
      }

      setTemplates(data);
    } catch (err) {
      setError('Failed to load templates');
      console.error('Error loading templates:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    setTabValue(newValue);
  };

  const getDifficultyColor = (difficulty: string) => {
    switch (difficulty) {
      case 'BEGINNER': return 'success';
      case 'INTERMEDIATE': return 'warning';
      case 'ADVANCED': return 'error';
      default: return 'default';
    }
  };

  const getGradeColor = (grade: string) => {
    switch (grade.toUpperCase()) {
      case 'A': return 'success';
      case 'B': return 'warning';
      case 'C': return 'error';
      default: return 'default';
    }
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <Typography>Loading templates...</Typography>
      </Box>
    );
  }

  if (error) {
    return (
      <Alert severity="error" sx={{ mt: 2 }}>
        {error}
      </Alert>
    );
  }

  if (selectedTemplate) {
    return (
      <Box>
        <Box sx={{ mb: 3 }}>
          <Button component={Link} to={`/templates${type ? `/${type}` : ''}`} variant="outlined">
            ← Back to Templates
          </Button>
        </Box>

        <Typography variant="h4" gutterBottom>
          {selectedTemplate.name}
        </Typography>

        <Box sx={{ mb: 3 }}>
          <Chip label={selectedTemplate.type} color="primary" sx={{ mr: 1 }} />
          <Chip label={selectedTemplate.difficulty} color={getDifficultyColor(selectedTemplate.metadata.difficulty)} sx={{ mr: 1 }} />
          <Chip label={`v${selectedTemplate.version}`} variant="outlined" />
        </Box>

        <Typography variant="body1" color="text.secondary" paragraph>
          {selectedTemplate.description}
        </Typography>

        <Card sx={{ mb: 3 }}>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              Template Overview
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6} md={3}>
                <Box sx={{ textAlign: 'center' }}>
                  <Typography variant="h4" color="primary.main">
                    {selectedTemplate.metadata.estimatedSetupTime}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Setup Time (min)
                  </Typography>
                </Box>
              </Grid>
              <Grid item xs={12} sm={6} md={3}>
                <Box sx={{ textAlign: 'center' }}>
                  <Typography variant="h4" color="primary.main">
                    {selectedTemplate.performanceMetrics.performanceGrade}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Performance Grade
                  </Typography>
                </Box>
              </Grid>
              <Grid item xs={12} sm={6} md={3}>
                <Box sx={{ textAlign: 'center' }}>
                  <Typography variant="h4" color="primary.main">
                    {selectedTemplate.reliabilityMetrics.reliabilityGrade}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Reliability Grade
                  </Typography>
                </Box>
              </Grid>
              <Grid item xs={12} sm={6} md={3}>
                <Box sx={{ textAlign: 'center' }}>
                  <Typography variant="h4" color="primary.main">
                    {selectedTemplate.performanceMetrics.concurrentUsers}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Max Users
                  </Typography>
                </Box>
              </Grid>
            </Grid>
          </CardContent>
        </Card>

        <Card sx={{ mb: 3 }}>
          <CardContent>
            <Tabs value={tabValue} onChange={handleTabChange} aria-label="template tabs">
              <Tab label="Code Template" />
              <Tab label="Configuration" />
              <Tab label="Usage Example" />
              <Tab label="Performance" />
              <Tab label="Reliability" />
            </Tabs>

            <TabPanel value={tabValue} index={0}>
              <Typography variant="h6" gutterBottom>
                Implementation Code
              </Typography>
              <SyntaxHighlighter language="java" style={tomorrow}>
                {selectedTemplate.codeTemplate}
              </SyntaxHighlighter>
            </TabPanel>

            <TabPanel value={tabValue} index={1}>
              <Typography variant="h6" gutterBottom>
                Configuration Example
              </Typography>
              <SyntaxHighlighter language="yaml" style={tomorrow}>
                {selectedTemplate.configurationExample}
              </SyntaxHighlighter>
            </TabPanel>

            <TabPanel value={tabValue} index={2}>
              <Typography variant="h6" gutterBottom>
                Usage Instructions
              </Typography>
              <SyntaxHighlighter language="bash" style={tomorrow}>
                {selectedTemplate.usageExample}
              </SyntaxHighlighter>
            </TabPanel>

            <TabPanel value={tabValue} index={3}>
              <Typography variant="h6" gutterBottom>
                Performance Metrics
              </Typography>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <List>
                    <ListItem>
                      <ListItemIcon><Speed /></ListItemIcon>
                      <ListItemText
                        primary={`${selectedTemplate.performanceMetrics.averageResponseTime}ms`}
                        secondary="Average Response Time"
                      />
                    </ListItem>
                    <ListItem>
                      <ListItemIcon><Assessment /></ListItemIcon>
                      <ListItemText
                        primary={`${selectedTemplate.performanceMetrics.throughput} req/sec`}
                        secondary="Throughput"
                      />
                    </ListItem>
                    <ListItem>
                      <ListItemIcon><Schedule /></ListItemIcon>
                      <ListItemText
                        primary={`${selectedTemplate.performanceMetrics.memoryUsage}MB`}
                        secondary="Memory Usage"
                      />
                    </ListItem>
                  </List>
                </Grid>
                <Grid item xs={12} sm={6}>
                  <List>
                    <ListItem>
                      <ListItemText
                        primary={`${selectedTemplate.performanceMetrics.cpuUsage}%`}
                        secondary="CPU Usage"
                      />
                    </ListItem>
                    <ListItem>
                      <ListItemText
                        primary={selectedTemplate.performanceMetrics.concurrentUsers}
                        secondary="Max Concurrent Users"
                      />
                    </ListItem>
                    <ListItem>
                      <ListItemText
                        primary={selectedTemplate.performanceMetrics.lastBenchmarked}
                        secondary="Last Benchmarked"
                      />
                    </ListItem>
                  </List>
                </Grid>
              </Grid>
            </TabPanel>

            <TabPanel value={tabValue} index={4}>
              <Typography variant="h6" gutterBottom>
                Reliability Metrics
              </Typography>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <List>
                    <ListItem>
                      <ListItemText
                        primary={`${selectedTemplate.reliabilityMetrics.uptime}%`}
                        secondary="Uptime"
                      />
                    </ListItem>
                    <ListItem>
                      <ListItemText
                        primary={`${selectedTemplate.reliabilityMetrics.availability}%`}
                        secondary="Availability"
                      />
                    </ListItem>
                    <ListItem>
                      <ListItemText
                        primary={`${selectedTemplate.reliabilityMetrics.errorRate}%`}
                        secondary="Error Rate"
                      />
                    </ListItem>
                  </List>
                </Grid>
                <Grid item xs={12} sm={6}>
                  <List>
                    <ListItem>
                      <ListItemText
                        primary={`${selectedTemplate.reliabilityMetrics.meanTimeBetweenFailures}h`}
                        secondary="MTBF (hours)"
                      />
                    </ListItem>
                    <ListItem>
                      <ListItemText
                        primary={`${selectedTemplate.reliabilityMetrics.meanTimeToRecovery}m`}
                        secondary="MTTR (minutes)"
                      />
                    </ListItem>
                    <ListItem>
                      <ListItemText
                        primary={selectedTemplate.reliabilityMetrics.lastIncident}
                        secondary="Last Incident"
                      />
                    </ListItem>
                  </List>
                </Grid>
              </Grid>
            </TabPanel>
          </CardContent>
        </Card>
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Integration Templates
      </Typography>

      {type && (
        <Box sx={{ mb: 3 }}>
          <Button component={Link} to="/templates" variant="outlined">
            ← Back to All Templates
          </Button>
        </Box>
      )}

      <Grid container spacing={3}>
        {templates.map((template) => (
          <Grid item xs={12} md={6} lg={4} key={template.id}>
            <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
              <CardContent sx={{ flexGrow: 1 }}>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 2 }}>
                  <Typography variant="h6" component="div">
                    {template.name}
                  </Typography>
                  <Chip
                    label={template.type}
                    size="small"
                    color="primary"
                  />
                </Box>

                <Typography variant="body2" color="text.secondary" paragraph>
                  {template.description}
                </Typography>

                <Box sx={{ mb: 2 }}>
                  <Chip
                    label={template.metadata.difficulty}
                    size="small"
                    color={getDifficultyColor(template.metadata.difficulty)}
                    sx={{ mr: 1 }}
                  />
                  <Chip
                    label={template.performanceMetrics.performanceGrade}
                    size="small"
                    color={getGradeColor(template.performanceMetrics.performanceGrade)}
                  />
                </Box>

                <Typography variant="body2">
                  <strong>Setup Time:</strong> {template.metadata.estimatedSetupTime} minutes
                </Typography>
                <Typography variant="body2">
                  <strong>Framework:</strong> {template.framework} {template.version}
                </Typography>
              </CardContent>

              <CardActions>
                <Button
                  size="small"
                  component={Link}
                  to={`/templates/${template.type}/${template.id}`}
                  startIcon={<Code />}
                >
                  View Details
                </Button>
                <Button
                  size="small"
                  startIcon={<GetApp />}
                  onClick={() => {
                    // In a real app, this would trigger a download
                    navigator.clipboard.writeText(template.codeTemplate);
                  }}
                >
                  Copy Code
                </Button>
              </CardActions>
            </Card>
          </Grid>
        ))}
      </Grid>

      {templates.length === 0 && (
        <Alert severity="info">
          No templates found for the selected criteria.
        </Alert>
      )}
    </Box>
  );
};

export default TemplatesPage;