package bonti.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String deadline;
    private OrderStatus status;
    private Terminal terminal;
    private Section section;
    @ElementCollection
    @CollectionTable(
            name = "order_medicines",
            joinColumns = @JoinColumn(name = "order_id")
    )
    @MapKeyJoinColumn(name = "medicine_id")
    @Column(name = "quantity")
    private Map<Medicine, Integer> medicines;
}
