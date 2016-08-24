package ve.com.gem.securities;

import io.jsonwebtoken.*;
import ve.com.gem.entities.Account;
import ve.com.gem.repositories.IAccountRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
	
  @Autowired
  private IAccountRepository accountRepository;

  private final Logger logger = Logger.getLogger(this.getClass());

  private final String AUDIENCE_UNKNOWN   = "unknown";
  private final String AUDIENCE_WEB       = "web";
  private final String AUDIENCE_MOBILE    = "mobile";
  private final String AUDIENCE_TABLET    = "tablet";

  @Value("${gem.token.secret}")
  private String secret;

  @Value("${gem.token.expiration}")
  private Long expiration;
  
  public String getUsernameFromToken(String token) {
    String username;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      username = claims.getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }

  public Timestamp getCreatedDateFromToken(String token) {
    Timestamp created;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      created = new Timestamp((Long) claims.get("created"));
    } catch (Exception e) {
      created = null;
    }
    return created;
  }

  public Timestamp getExpirationDateFromToken(String token) {
    Timestamp expiration;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      expiration = new Timestamp(claims.getExpiration().getTime()); 
      
      System.out.println("expiration: " + expiration);
    } catch (Exception e) {
      expiration = null;
    }
    return expiration;
  }

  public String getAudienceFromToken(String token) {
    String audience;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      audience = (String) claims.get("audience");
    } catch (Exception e) {
      audience = null;
    }
    return audience;
  }

  private Claims getClaimsFromToken(String token) {
    Claims claims;
    try {
      claims = Jwts.parser()
        .setSigningKey(this.secret)
        .parseClaimsJws(token)
        .getBody();
    } catch (Exception e) {
      claims = null;
    }
    return claims;
  }

  private Timestamp generateCurrentDate() {
	Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    return timestamp;
  }

  private Timestamp generateExpirationDate() {
    return new Timestamp(System.currentTimeMillis() + this.expiration * 1000);
  }

  private Boolean isTokenExpired(String token) {
    final Timestamp expiration = this.getExpirationDateFromToken(token);
    return expiration.before(this.generateCurrentDate());
  }

  private Boolean isCreatedBeforeLastPasswordReset(Timestamp created, Timestamp lastPasswordReset) {
    return (lastPasswordReset != null && created.before(lastPasswordReset));
  }

  private String generateAudience(Device device) {
    String audience = this.AUDIENCE_UNKNOWN;
    if (device.isNormal()) {
      audience = this.AUDIENCE_WEB;
    } else if (device.isTablet()) {
      audience = AUDIENCE_TABLET;
    } else if (device.isMobile()) {
      audience = AUDIENCE_MOBILE;
    }
    return audience;
  }

  private Boolean ignoreTokenExpiration(String token) {
    String audience = this.getAudienceFromToken(token);
    return (this.AUDIENCE_TABLET.equals(audience) || this.AUDIENCE_MOBILE.equals(audience));
  }

  public String generateToken(UserDetails userDetails, Device device) {
    Map<String, Object> claims = new HashMap<String, Object>();
    claims.put("sub", userDetails.getUsername());
    claims.put("audience", this.generateAudience(device));
    claims.put("created", this.generateCurrentDate());
    return this.generateToken(claims);
  }

  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
      .setClaims(claims)
      .setExpiration(this.generateExpirationDate())
      .signWith(SignatureAlgorithm.HS512, this.secret)
      .compact();
  }

  public Boolean canTokenBeRefreshed(String token, Timestamp lastPasswordReset) {
    final Timestamp created = Timestamp.valueOf(this.getCreatedDateFromToken(token).toString());
    return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset)) && (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
  }

  public String refreshToken(String token) {
    String refreshedToken;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      claims.put("created", this.generateCurrentDate());
      refreshedToken = this.generateToken(claims);
    } catch (Exception e) {
      refreshedToken = null;
    }
    return refreshedToken;
  }

  public Boolean validateToken(String token, User user) {
    //AccountUserDetails customUser = new AccountUserDetails(user);
	final String username = this.getUsernameFromToken(token);
    final Timestamp created = Timestamp.valueOf(this.getCreatedDateFromToken(token).toString());
    //final Date expiration = this.getExpirationDateFromToken(token);
    
    Account account = accountRepository.findByUsername(username);
    final Timestamp lastPasswordReset = account.getLastPasswordReset();
    final Boolean isActive = account.getIsActive();
    
    if (null != isActive)
    	return (username.equals(user.getUsername()) && !(this.isTokenExpired(token))) && !(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset)) && (isActive == true);
    else 
    	return false;
  }

}
