import React from 'react';
import {
  Typography,
  Grid,
  Card,
  CardContent,
  CardActions,
  Button,
  Box,
  Chip,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import {
  IntegrationInstructions,
  Speed,
  Description,
  Analytics,
  Code,
  Cloud,
  Storage,
  Web,
} from '@mui/icons-material';
import { Link } from 'react-router-dom';
import { IntegrationType } from '../types';

const HomePage: React.FC = () => {
  const integrationTypes = [
    { type: IntegrationType.REST, label: 'REST APIs', icon: <Web />, color: '#1976d2' },
    { type: IntegrationType.KAFKA, label: 'Apache Kafka', icon: <IntegrationInstructions />, color: '#388e3c' },
    { type: IntegrationType.SOAP, label: 'SOAP Services', icon: <Code />, color: '#f57c00' },
    { type: IntegrationType.DATABASE, label: 'Databases', icon: <Storage />, color: '#7b1fa2' },
  ];

  const features = [
    {
      title: 'Code Templates',
      description: 'Ready-to-use integration code templates with best practices',
      icon: <Code fontSize="large" />,
      link: '/templates',
    },
    {
      title: 'Performance Insights',
      description: 'Detailed performance metrics and benchmarking data',
      icon: <Speed fontSize="large" />,
      link: '/performance',
    },
    {
      title: 'Documentation',
      description: 'Comprehensive guides and tutorials for all integrations',
      icon: <Description fontSize="large" />,
      link: '/docs',
    },
    {
      title: 'Monitoring',
      description: 'Real-time metrics and observability for your integrations',
      icon: <Analytics fontSize="large" />,
      link: '/metrics',
    },
  ];

  return (
    <Box>
      {/* Hero Section */}
      <Box sx={{ textAlign: 'center', mb: 6, py: 4 }}>
        <Typography variant="h3" component="h1" gutterBottom>
          Developer Integration Portal
        </Typography>
        <Typography variant="h6" color="text.secondary" sx={{ mb: 3 }}>
          Your one-stop destination for production-ready integration templates,
          performance insights, and reliability metrics
        </Typography>
        <Box sx={{ display: 'flex', gap: 1, justifyContent: 'center', flexWrap: 'wrap' }}>
          <Chip label="Spring Boot 3.x" color="primary" />
          <Chip label="Java 17" color="secondary" />
          <Chip label="Production Ready" color="success" />
          <Chip label="Open Source" color="info" />
        </Box>
      </Box>

      {/* Integration Types */}
      <Box sx={{ mb: 6 }}>
        <Typography variant="h4" gutterBottom>
          Supported Integration Types
        </Typography>
        <Grid container spacing={3}>
          {integrationTypes.map((item) => (
            <Grid item xs={12} sm={6} md={3} key={item.type}>
              <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
                <CardContent sx={{ flexGrow: 1 }}>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                    <Box sx={{ color: item.color, mr: 1 }}>
                      {item.icon}
                    </Box>
                    <Typography variant="h6">
                      {item.label}
                    </Typography>
                  </Box>
                  <Typography variant="body2" color="text.secondary">
                    Production-ready {item.label.toLowerCase()} integration templates with comprehensive documentation
                  </Typography>
                </CardContent>
                <CardActions>
                  <Button size="small" component={Link} to={`/templates/${item.type}`}>
                    View Templates
                  </Button>
                  <Button size="small" component={Link} to={`/docs/${item.type}`}>
                    Documentation
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Box>

      {/* Features */}
      <Box sx={{ mb: 6 }}>
        <Typography variant="h4" gutterBottom>
          Portal Features
        </Typography>
        <Grid container spacing={4}>
          {features.map((feature, index) => (
            <Grid item xs={12} sm={6} md={3} key={index}>
              <Card sx={{ height: '100%', textAlign: 'center' }}>
                <CardContent>
                  <Box sx={{ color: 'primary.main', mb: 2 }}>
                    {feature.icon}
                  </Box>
                  <Typography variant="h6" gutterBottom>
                    {feature.title}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {feature.description}
                  </Typography>
                </CardContent>
                <CardActions sx={{ justifyContent: 'center' }}>
                  <Button component={Link} to={feature.link}>
                    Explore
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Box>

      {/* Quick Stats */}
      <Box sx={{ mb: 6 }}>
        <Typography variant="h4" gutterBottom>
          Platform Statistics
        </Typography>
        <Grid container spacing={3}>
          <Grid item xs={12} sm={6} md={3}>
            <Card>
              <CardContent sx={{ textAlign: 'center' }}>
                <Typography variant="h3" color="primary.main">
                  4+
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Integration Types
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Card>
              <CardContent sx={{ textAlign: 'center' }}>
                <Typography variant="h3" color="primary.main">
                  100%
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Production Ready
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Card>
              <CardContent sx={{ textAlign: 'center' }}>
                <Typography variant="h3" color="primary.main">
                  24/7
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Monitoring
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Card>
              <CardContent sx={{ textAlign: 'center' }}>
                <Typography variant="h3" color="primary.main">
                  MIT
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Open Source
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Box>

      {/* Getting Started */}
      <Box sx={{ textAlign: 'center', py: 4 }}>
        <Typography variant="h5" gutterBottom>
          Ready to get started?
        </Typography>
        <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
          Choose an integration type above or browse all available templates
        </Typography>
        <Button
          variant="contained"
          size="large"
          component={Link}
          to="/templates"
          startIcon={<IntegrationInstructions />}
        >
          Browse All Templates
        </Button>
      </Box>
    </Box>
  );
};

export default HomePage;