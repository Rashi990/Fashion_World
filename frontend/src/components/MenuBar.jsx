import React from "react";
import { AppBar, Toolbar, Button, Container, IconButton, Menu, MenuItem, Box } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import { colors } from "../theme/colors";

const pages = ["Home","Women", "Men", "Kids", "Beauty", "Accessories", "Sale", "Shops", "About Us", "Contact Us"];

function MenuBar() {
  const [anchorElNav, setAnchorElNav] = React.useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  return (
    <AppBar 
      position="static" 
      elevation={0}
      sx={{ background: colors.menuBar }}
    >
      <Container maxWidth="lg">
        <Toolbar disableGutters>

          {/* Mobile menu icon */}
          <Box sx={{ display: { xs: "flex", md: "none" } }}>
            <IconButton onClick={handleOpenNavMenu}>
              <MenuIcon sx={{ color: colors.textDark }} />
            </IconButton>

            <Menu
              anchorEl={anchorElNav}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
            >
              {pages.map((page) => (
                <MenuItem key={page} onClick={handleCloseNavMenu}>
                  {page}
                </MenuItem>
              ))}
            </Menu>
          </Box>

          {/* Desktop menu */}
          <Box
            sx={{
              flexGrow: 1,
              display: { xs: "none", md: "flex" },
              justifyContent: "center",
              gap: 4,
            }}
          >
            {pages.map((page) => (
              <Button
                key={page}
                sx={{
                    color: colors.white,
                    fontWeight: 650,
                    fontSize: "0.98rem",
                    position: "relative",
                    "&:hover": {
                        color: colors.blush,
                        background: "transparent",
                    },
                    "&::after": {
                        content: '""',
                        position: "absolute",
                        width: "0%",
                        height: "2px",
                        bottom: 0,
                        left: 0,
                        backgroundColor: colors.primary,
                        transition: "0.3s",
                    },
                    "&:hover::after": {
                        width: "100%",
                    },
                    }}

              >
                {page}
              </Button>
            ))}
          </Box>

        </Toolbar>
      </Container>
    </AppBar>
  );
}

export default MenuBar;
