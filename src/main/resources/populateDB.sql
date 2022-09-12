insert into developers (name, username, salary)
values ('Serhii Babanov', 'serhii_babanov', 5000);
insert into developers (name, username, salary)
values ('Andrii Zerko', 'andrii_zerko', 4800);
insert into developers (name, username, salary)
values ('Anna Zadoiko', 'anna_zadoiko', 5100);
insert into developers (name, username, salary)
values ('Kostiantyn Maslak', 'kostiantyn_maslak', 6500);

insert into companies (name, country)
values ('Google', 'Canada');
insert into companies (name, country)
values ('GoIT', 'Ukraine');
insert into companies (name, country)
values ('IBM', 'USA');
insert into companies (name, country)
values ('Honda', 'Japan');

insert into customers (name, email)
values ('Oleksandr Yanov', 'oleksandr.yanov@example.com');
insert into customers (name, email)
values ('Cофия Сидоренко', 'sofiia.sidorenko@example.com');
insert into customers (name, email)
values ('Dima Aushev', 'dima.aushev@example.com');
insert into customers (name, email)
values ('Viktoriia Shynkar', 'viktoriia.shynkar@example.com');

insert into projects (name, git_url, cost, date)
values ('SQLDiagramManager', 'localhost:1001/SQLDiagramManager.git', 500000, 1662073320000);
insert into projects (name, git_url, cost, date)
values ('SQLSyntaxChecker', 'localhost:1001/SQLSyntaxChecker.git', 730000, 1629586920000);
insert into projects (name, git_url, cost, date)
values ('TimeManagingSystem',
        'localhost:1001/TimeManagingSystem.git', 480000, 1636239720000);
insert into projects (name, git_url, cost, date)
values ('ImageSearchEngine', 'localhost:1001/ImageSearchEngine.git', 365000, 1589151720000);
insert into projects (name, git_url, cost, date)
values ('CylinderDeactivationControlSystem',
        'localhost:1001/CylinderDeactivationControlSystem.git', 120050, 1621551720000);

insert into skills (language, level)
values ('Java', 'Junior');
insert into skills (language, level)
values ('Java', 'Middle');
insert into skills (language, level)
values ('Java', 'Senior');
insert into skills (language, level)
values ('SQL', 'Junior');
insert into skills (language, level)
values ('SQL', 'Middle');
insert into skills (language, level)
values ('SQL', 'Senior');
insert into skills (language, level)
values ('C', 'Junior');
insert into skills (language, level)
values ('C', 'Middle');
insert into skills (language, level)
values ('C', 'Senior');
insert into skills (language, level)
values ('JavaScript', 'Junior');
insert into skills (language, level)
values ('JavaScript', 'Middle');
insert into skills (language, level)
values ('JavaScript', 'Senior');

insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'SQLDiagramManager'
  and d.username = 'serhii_babanov';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'SQLDiagramManager'
  and d.username = 'andrii_zerko';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'SQLSyntaxChecker'
  and d.username = 'andrii_zerko';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'SQLSyntaxChecker'
  and d.username = 'anna_zadoiko';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'TimeManagingSystem'
  and d.username = 'anna_zadoiko';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'TimeManagingSystem'
  and d.username = 'kostiantyn_maslak';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'ImageSearchEngine'
  and d.username = 'kostiantyn_maslak';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'ImageSearchEngine'
  and d.username = 'andrii_zerko';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'ImageSearchEngine'
  and d.username = 'anna_zadoiko';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'CylinderDeactivationControlSystem'
  and d.username = 'serhii_babanov';
insert into project_developer_relation (project_id, developer_id)
select p.id, d.id
from projects as p,
     developers as d
where p.name = 'CylinderDeactivationControlSystem'
  and d.username = 'kostiantyn_maslak';


insert into developer_skill_relation (developer_id, skill_id)
select d.id, s.id
from developers as d,
     skills as s
where d.username = 'serhii_babanov'
  and s.language = 'Java'
  and s.level = 'Junior';
insert into developer_skill_relation (developer_id, skill_id)
select d.id, s.id
from developers as d,
     skills as s
where d.username = 'serhii_babanov'
  and s.language = 'SQL'
  and s.level = 'Middle';
insert into developer_skill_relation (developer_id, skill_id)
select d.id, s.id
from developers as d,
     skills as s
where d.username = 'andrii_zerko'
  and s.language = 'Java'
  and s.level = 'Senior';
insert into developer_skill_relation (developer_id, skill_id)
select d.id, s.id
from developers as d,
     skills as s
where d.username = 'andrii_zerko'
  and s.language = 'C'
  and s.level = 'Junior';
insert into developer_skill_relation (developer_id, skill_id)
select d.id, s.id
from developers as d,
     skills as s
where d.username = 'anna_zadoiko'
  and s.language = 'SQL'
  and s.level = 'Middle';
insert into developer_skill_relation (developer_id, skill_id)
select d.id, s.id
from developers as d,
     skills as s
where d.username = 'anna_zadoiko'
  and s.language = 'JavaScript'
  and s.level = 'Middle';
insert into developer_skill_relation (developer_id, skill_id)
select d.id, s.id
from developers as d,
     skills as s
where d.username = 'kostiantyn_maslak'
  and s.language = 'C'
  and s.level = 'Senior';
insert into developer_skill_relation (developer_id, skill_id)
select d.id, s.id
from developers as d,
     skills as s
where d.username = 'kostiantyn_maslak'
  and s.language = 'JavaScript'
  and s.level = 'Junior';


