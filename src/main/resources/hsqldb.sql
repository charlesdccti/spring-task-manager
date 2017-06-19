INSERT INTO users (user_name, user_last_name, password, email,
                   enabled, is_admin, registered) VALUES
  ('Eric', 'Cartman', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'eric_cartman@gmail.com', TRUE, FALSE, '2017-05-09'),
  ('Kenny', 'McKormick', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'kenny_mckormick@gmail.com', TRUE, FALSE, '2017-06-07'),
  ('Kyle', 'Broflovski', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'kyle_broflovski@gmail.com', TRUE, FALSE, '2017-04-09'),
  ('Stanley', 'Marsh', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'stanley_marsh@gmail.com', TRUE, FALSE, '2017-06-09'),
  ('Barbara', 'Stevens', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'barbara_stevens@gmail.com', TRUE, TRUE, '2017-05-11'),
  ('Token', 'Black', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'token_black@gmail.com', FALSE, FALSE, '2017-04-09'),
  ('Sharon', 'Marsh', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'sharon_marsh@gmail.com', TRUE, FALSE, '2017-04-08'),
  ('Lien', 'Cartman', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'lien_cartman@gmail.com', TRUE, FALSE, '2017-04-08'),
  ('Butters', 'Scotch', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'butters_scotch@gmail.com', TRUE, FALSE, '2017-04-08'),
  ('Ike', 'Broflovski', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'ike_broflovski@gmail.com', TRUE, FALSE, '2017-04-08'),
  ('Timmy', 'Timmy', '$2a$10$vuEKDsiqD48.nKXpuWLmMu9KGzuJj2b855BMEnaRxbG/tUy5zXoBq',
   'timmy_timmy@gmail.com', FALSE, FALSE, '2017-05-09');

INSERT INTO tasks (description, estimated_days, is_new_shared, is_completed, created_by_user_with_email,
                   last_update_description, last_update_date) VALUES
  ('learn Java', 20, FALSE, TRUE, 'eric_cartman@gmail.com', 'edit description', '2017-06-07'),
  ('learn C', 15, TRUE, FALSE, 'kenny_mckormick@gmail.com', '', '2017-06-17'),
  ('learn C++', 30, TRUE, FALSE, 'kyle_broflovski@gmail.com', 'completed', '2017-06-02'),
  ('learn C#', 25, FALSE, FALSE, 'stanley_marsh@gmail.com', 'completed', '2017-05-29'),
  ('learn Linux', 10, TRUE, FALSE, 'barbara_stevens@gmail.com', 'edit estimate', '2017-06-20'),
  ('learn Sql/noSql', 27, FALSE, TRUE, 'eric_cartman@gmail.com', 'created', '2017-05-28');

INSERT INTO user_roles (user_id, role) VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_USER'),
  (3, 'ROLE_USER'),
  (4, 'ROLE_USER'),
  (5, 'ROLE_ADMIN'),
  (5, 'ROLE_USER'),
  (6, 'ROLE_USER'),
  (7, 'ROLE_USER'),
  (8, 'ROLE_USER'),
  (9, 'ROLE_USER'),
  (10, 'ROLE_USER'),
  (11, 'ROLE_USER');

INSERT INTO users_tasks (user_id, task_id) VALUES
  (1, 1),
  (2, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (7, 5),
  (1, 6),
  (2, 6);