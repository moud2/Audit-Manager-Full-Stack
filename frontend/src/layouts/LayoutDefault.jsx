import { Header } from "../components/Layout/Header.jsx";
import * as React from 'react';
import { styled, useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import CssBaseline from '@mui/material/CssBaseline';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import {Children} from "react";
import { Link } from "react-router-dom";
import { useSidebar } from "./SidebarContext.jsx";

const drawerWidth = 240;

const Main = styled('main', {
    shouldForwardProp: (prop) => prop !== 'open',
  })(({ theme, open }) => ({
    flexGrow: 1,
    padding: theme.spacing(3),
    marginLeft: open ? 0 : `-${drawerWidth}px`,
    transition: theme.transitions.create('margin', {
      easing: open
        ? theme.transitions.easing.easeOut
        : theme.transitions.easing.sharp,
      duration: open
        ? theme.transitions.duration.enteringScreen
        : theme.transitions.duration.leavingScreen,
    }),
  }));

const DrawerHeader = styled('div')(({ theme }) => 
  ({display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,justifyContent: 'flex-end',}));

export function LayoutDefault({children}) {
  const theme = useTheme();
  const { open, closeSidebar, openSidebar } = useSidebar();
  
  const links = 
  [{ href: "/dashboard", label: "Dashboard" },];

  return (
    <Box sx={{display: 'flex'}}>
      <CssBaseline/>
      <Header/>
      <IconButton color="inherit" 
        aria-label="open drawer" 
        onClick={openSidebar} 
        edge="start" 
        sx={{position: "fixed",top: "16px",left: "215px",zIndex: 1301,display: open ? "none" : "block",}}>
        <MenuIcon/>
      </IconButton>
     <Drawer 
        sx={{width: drawerWidth, flexShrink: 0,'& .MuiDrawer-paper': {width: drawerWidth,boxSizing: 'border-box',},}} 
        variant="persistent" 
        anchor="left" 
        open={open}>

        <DrawerHeader>
        <div style={{ display: "flex", alignItems: "center" }}>
            <img
              src="/logo-insight.png"
              alt="Logo"
              style={{ width: "32px", height: "32px", marginRight: "8px" }}
            />
            <h2 style={{ margin: 0, fontSize: "1.2rem" }}>InSight</h2>
          </div>

          <IconButton onClick={closeSidebar}>
            {theme.direction === 'ltr' ? <ChevronLeftIcon/> : <ChevronRightIcon/>}
          </IconButton>
        </DrawerHeader>
        <Divider/>
        <List>
          {links.map((link, index) => (
          <ListItem key={index} disablePadding>
          <ListItemButton component={Link} to={link.href}>
          <ListItemText primary={link.label}/>
          </ListItemButton>
          </ListItem>))}
        </List>
        <Divider/>
      </Drawer>
      <Main open={open} className="flex-1 ml-64 bg-gray-100 pb-24">
        <DrawerHeader/>
        {Children.map(children, (child) => child)}
      </Main>
    </Box>
  );
}