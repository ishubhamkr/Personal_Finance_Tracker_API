package personal.finance.tracker.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.finance.tracker.entity.User;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2 hours
    private static final String ROLE_KEY = "role"; // Key for user role in token

    // Generate JWT Token
    public String generateToken(User user) {
        try {
            logger.info("Generating token for user: {}", user.getUserName());

            return Jwts.builder()
                    .setSubject(user.getId()) // Set user ID as the subject
                    .claim("userName", user.getUserName())
                    .claim("name", user.getName())
                    .claim("email", user.getEmail())
                    .claim("phoneNumber", user.getPhoneNumber())
                    .claim("address", user.getAddress())
                    .claim(ROLE_KEY, "USER") // Hardcoded role; make dynamic if needed
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating JWT token", e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    // Validate JWT Token
    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            logger.error("Error validating token", e);
            return false;
        }
    }

    // Extract Username from Token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Role from Token
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get(ROLE_KEY, String.class));
    }

    // Check if Token is Expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract Expiration Date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract Specific Claim
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = parseClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parse Claims from Token
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Error parsing claims from token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    // Get Signing Key (from Base64 encoded secret key)
    private SecretKey getSigningKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid secret key: {}", e.getMessage());
            throw new RuntimeException("Failed to decode secret key", e);
        }
    }
}
