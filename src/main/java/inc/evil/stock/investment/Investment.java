package inc.evil.stock.investment;

import inc.evil.stock.entity.AbstractEntity;
import inc.evil.stock.user.User;

import javax.persistence.*;

@Entity
@Table(name = "investments")
public class Investment extends AbstractEntity implements Comparable<Investment> {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "symbol", unique = true, nullable = false)
    private String symbol;

    protected Investment() {
    }

    public Investment(User user, String name) {
        this.user = user;
        this.name = name;
    }

    private Investment(InvestmentBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.user = builder.user;
        this.symbol = builder.symbol;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public static InvestmentBuilder builder() {
        return new InvestmentBuilder();
    }

    @Override
    public String toString() {
        return "Investment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public int compareTo(Investment other) {
        int compareResult = user.getId().compareTo(other.getUser().getId());
        return (compareResult == 0) ? name.compareTo(other.getName()) : compareResult;
    }

    public static class InvestmentBuilder {
        private String id;
        private String name;
        private User user;
        private String symbol;

        public InvestmentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public InvestmentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public InvestmentBuilder user(User user) {
            this.user = user;
            return this;
        }

        public InvestmentBuilder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Investment build() {
            return new Investment(this);
        }
    }
}
