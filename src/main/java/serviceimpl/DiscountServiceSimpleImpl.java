package serviceimpl;

import domain.Event;
import domain.User;
import service.DiscountService;
import service.DiscountStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DiscountServiceSimpleImpl implements DiscountService {
    private List<DiscountStrategy> discountStrategies;

    public DiscountServiceSimpleImpl(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    public void init() {
        discountStrategies.sort((s1, s2) -> {
            if (s1 == null && s2 == null)
                return 0;

            if (s1 == null)
                return 1;

            if (s2 == null)
                return -1;

            return Byte.compare(s2.getDiscountSize(), s1.getDiscountSize());
        });
    }

    @Override
    public byte getDiscount(User user, Event event, LocalDateTime airDateTime,
                            long numberOfTickets) {
        Optional<DiscountStrategy> optDiscountStrategy =
                discountStrategies.stream().filter(d -> d.hasDiscount(user, event, airDateTime, numberOfTickets)).
                findFirst();

        return optDiscountStrategy.map(DiscountStrategy::getDiscountSize).orElse((byte) 0);
    }
}
