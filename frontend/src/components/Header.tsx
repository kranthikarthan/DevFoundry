import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
  Chip,
} from '@mui/material';
import {
  IntegrationInstructions,
  Description,
  Speed,
  Analytics,
} from '@mui/icons-material';

const Header: React.FC = () => {
  const location = useLocation();

  const navigationItems = [
    { path: '/', label: 'Home', icon: <IntegrationInstructions /> },
    { path: '/templates', label: 'Templates', icon: <IntegrationInstructions /> },
    { path: '/docs', label: 'Documentation', icon: <Description /> },
    { path: '/performance', label: 'Performance', icon: <Speed /> },
    { path: '/metrics', label: 'Metrics', icon: <Analytics /> },
  ];

  return (
    <AppBar position="static" elevation={2}>
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1, display: 'flex', alignItems: 'center' }}>
          <IntegrationInstructions sx={{ mr: 1 }} />
          Developer Integration Portal
        </Typography>

        <Box sx={{ display: 'flex', gap: 1 }}>
          {navigationItems.map((item) => (
            <Button
              key={item.path}
              component={Link}
              to={item.path}
              variant={location.pathname === item.path ? 'contained' : 'text'}
              color="inherit"
              startIcon={item.icon}
              sx={{
                color: location.pathname === item.path ? 'primary.contrastText' : 'inherit',
              }}
            >
              {item.label}
            </Button>
          ))}
        </Box>

        <Chip
          label="v1.0.0"
          size="small"
          sx={{ ml: 2, bgcolor: 'rgba(255, 255, 255, 0.1)', color: 'white' }}
        />
      </Toolbar>
    </AppBar>
  );
};

export default Header;