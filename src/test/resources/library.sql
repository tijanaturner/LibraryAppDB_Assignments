-- US01
select count(id) from users;
-- 1855

select count(distinct id) from users;

select count(*) from book_borrow
where is_returned = '0';

select count(*) from book_borrow
where is_returned = '0';

select * from book_borrow;


select bc.name,count(*) from book_borrow bb
inner join books b on bb.book_id = b.id
inner join book_categories bc on b.book_category_id=bc.id
group by name
order by 2 desc;






