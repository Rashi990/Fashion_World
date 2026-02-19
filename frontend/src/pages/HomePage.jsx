import React from "react";
import NavBar from "../components/NavBar";
import MenuBar from "../components/MenuBar";
import bgGirl from "../assets/bgGirl.png";
import { Box, Typography, Button, Container } from "@mui/material";
import { colors } from "../theme/colors";

function HomePage() {
  return (
<div
  style={{
    background: `linear-gradient(
      120deg,
      ${colors.orchid} 0%,
      ${colors.white} 35%,
      ${colors.amethyst} 100%
    )`,
    minHeight: "100vh",
  }}
>
      <NavBar />
      <MenuBar />

      <Container maxWidth="lg">
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between",
            minHeight: "80vh",
          }}
        >
          {/* LEFT SIDE TEXT */}
          <Box sx={{ width: "50%" }}>
            <Typography
              variant="h3"
              sx={{
                fontWeight: 800,
                color: colors.textDark,
                mb: 2,
                lineHeight: 1.2,
              }}
            >
              Discover Your
              <span style={{ color: colors.primary }}> Perfect Style</span>
            </Typography>

            <Typography
              sx={{
                color: colors.textGrey,
                fontSize: "18px",
                mb: 4,
                lineHeight: 1.7,
              }}
            >
              Explore the latest trends in fashion. Dresses, shoes, accessories and more — everything you need to look amazing.
            </Typography>

            <Button
              variant="contained"
              sx={{
                background: colors.primary,
                padding: "14px 34px",
                fontSize: "16px",
                borderRadius: "30px",
                boxShadow: "0 8px 20px rgba(230,0,169,0.25)",
                "&:hover": {
                  background: colors.primaryDark,
                },
              }}
            >
              Shop Now
            </Button>
          </Box>

          {/* RIGHT SIDE IMAGE */}
          <Box sx={{ width: "50%", textAlign: "right" }}>
            <img
              src={bgGirl}
              alt="fashion"
              style={{
                maxHeight: "650px",
                width: "100%",
                objectFit: "contain",
              }}
            />
          </Box>
        </Box>
      </Container>
    </div>
  );
}

export default HomePage;
