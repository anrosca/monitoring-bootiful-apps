create or replace view v_portfolio_summary(user_id, stock_symbol, total_amount, total_spent) as
select u.id, ir.symbol, sum(amount_bought) total_amount, sum(spent) total_spent
from investment_records ir
         join investments i on i.id = ir.investment_id
         join users u on u.id = i.user_id
group by u.id, ir.symbol;
