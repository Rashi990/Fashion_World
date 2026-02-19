import React from 'react'
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import IconButton from '@mui/material/IconButton';
import Badge from '@mui/material/Badge';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { colors } from '../theme/colors';
import SearchIcon from '@mui/icons-material/Search';
import logo from "../assets/logo.png";
import Avatar from '@mui/material/Avatar';

function NavBar() {
  return (

    <nav 
      className="navbar shadow-sm py-2" 
      style={{ 
        background: colors.lavender,
        borderBottom: `px solid ${colors.periwinkle}`
        }}>

      <div className="container-fluid d-flex align-items-center justify-content-between">

{/* Logo */}
        <div className="d-flex align-items-center">
          <img src={logo} alt="logo" height="65" />
        </div>

{/* Search bar */}
        <div
          className="d-none d-md-flex align-items-center"
          style={{
            width: "40%",
            background: colors.white,                 
            borderRadius: "30px",
            padding: "10px 20px",
            border: `1px solid ${colors.periwinkle}`,
            boxShadow: "0 4px 14px rgba(156,37,184,0.25)"
          }}
        >
          <SearchIcon sx={{ color: colors.primary, mr: 1, fontSize: 26 }} />

          <input
            type="text"
            placeholder="Search for dresses, shoes, bags..."
            style={{
              border: "none",
              outline: "none",
              background: "transparent",
              width: "100%",
              fontSize: "15px"
            }}
          />
        </div>

        <div className="d-flex align-items-center gap-4">

          <IconButton>
            <Badge
              badgeContent={3}
              sx={{
                "& .MuiBadge-badge": {
                  background: colors.primary,
                  color: "#fff",
                  fontWeight: "bold"
                }
              }}
            >
              <ShoppingCartIcon sx={{ color: colors.textDark, fontSize: 30 }} />
            </Badge>
          </IconButton>

          <IconButton>
              <Avatar 
                sx={{ 
                  bgcolor: colors.primary,
                  width: 36,
                  height: 36,
                  fontWeight: "bold"
                }}
              >
                R
              </Avatar>

          </IconButton>

        </div>

      </div>
    </nav>

  )
}

export default NavBar
