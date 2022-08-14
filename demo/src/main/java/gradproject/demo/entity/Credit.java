package gradproject.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="CREDIT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date applicationDate;
    @Column(unique = true)
    private Long userIdentityNumber;
    private float creditAmount;
    private String creditResult;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}