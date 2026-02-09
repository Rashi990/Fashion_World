import React from 'react'
import Button from '@mui/material/Button';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

function NavBar() {
  return (
    <div>NavBar
        <Button variant="text">Text</Button>
        <Button variant="contained">Contained</Button>
        <Button variant="outlined">Outlined</Button>

        <AccountCircleIcon></AccountCircleIcon>
    </div>
  )
}

export default NavBar