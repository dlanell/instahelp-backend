ALTER TABLE volunteer_request ADD UNIQUE unique_name_title_date(name, title, date);