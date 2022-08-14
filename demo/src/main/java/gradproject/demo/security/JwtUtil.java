package gradproject.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    // hash işlemi yaparken kullanılacak key
    //SECRET_KEY değeri algoritma çalışırken kullanılacak hash değeridir.
    private String SECRET_KEY = "cozef";

    // verilen token a ait kullanıcı adını döndürür.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // verilen token a ait token bitiş süresini verir.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // verilen token a ait claims bilgisini alır.
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // token ın geçerlilik süre doldu mu?
    //isTokenExpired metodu tokenın süresinin dolup dolmadığına kontrol eder.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // userDetails objesini alır. createToken metoduna gönderir.
    //generateToken motodu userDetails nesnesi alır ve bize token değerini döner.
    //createToken metodunu çağırır. Bu metot da token ı oluşturan bazı bilgiler girilir.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        //setClaims(claims) → claims nesnesi atanır.
        return Jwts.builder().setClaims(claims)
                //.setSubject(subject) → ilgili kullanıcı bilgisi atanır.
                .setSubject(subject) // ilgili kullanıcı
                //.setIssuedAt(new Date(System.currentTimeMillis())) → tokenın başlangıç zamanı
                .setIssuedAt(new Date(System.currentTimeMillis())) // başlangıç
                //.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000)) → tokenın bitiş zamanı
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000)) // bitiş
                // .signWith(SignatureAlgorithm.HS256, SECRET_KEY) → kullanılan algoritma ve bu algoritma çalışırken kullanılacak key değeri
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // kullanılan algoritma ve bu algoritma çalışırken kullanılacak hash key değeri
                .compact();
    }

    // token hala geçerli mi? kullanıcı adı doğru ise ve token ın geçerlilik süresi devam ediyorsa true döner.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}