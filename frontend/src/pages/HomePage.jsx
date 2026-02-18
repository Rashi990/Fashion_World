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
      180deg,
      ${colors.white} 0%,
      ${colors.background} 40%,
      #FFF0F7 100%
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
                fontWeight: 700,
                color: colors.textDark,
                mb: 2,
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
                lineHeight: 1.6,
              }}
            >
              Explore the latest trends in fashion. Dresses, shoes, accessories and more — everything you need to look amazing.
            </Typography>

            <Button
              variant="contained"
              sx={{
                background: colors.primary,
                padding: "12px 28px",
                fontSize: "16px",
                borderRadius: "30px",
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
