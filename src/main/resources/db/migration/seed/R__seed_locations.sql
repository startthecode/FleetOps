-- R__seed_locations.sql
-- Repeatable seed for the location hierarchy: countries -> states -> cities.
-- Idempotent: ON CONFLICT (unique name) DO NOTHING, so re-running is safe.
-- Uses nextval(...) so ids stay in sync with the Hibernate sequences, and
-- references parents by their unique name (not hard-coded ids).
-- Seeds 3 countries, 14 states, 31 cities.

-- ── countries ──────────────────────────────────────────────────────────────
INSERT INTO countries (country_id, country_name)
SELECT nextval('country_id_generator'), v.name
FROM (VALUES ('India'), ('United States'), ('United Kingdom')) AS v(name)
ON CONFLICT (country_name) DO NOTHING;

-- ── states (linked to their country by name) ─────────────────────────────────
INSERT INTO states (state_id, state_name, country)
SELECT nextval('state_id_generator'), v.state_name, c.country_id
FROM (VALUES
        ('Delhi',          'India'),
        ('Maharashtra',    'India'),
        ('Karnataka',      'India'),
        ('Tamil Nadu',     'India'),
        ('Telangana',      'India'),
        ('Uttar Pradesh',  'India'),
        ('West Bengal',    'India'),
        ('Gujarat',        'India'),
        ('Rajasthan',      'India'),
        ('Punjab',         'India'),
        ('California',     'United States'),
        ('Texas',          'United States'),
        ('New York',       'United States'),
        ('England',        'United Kingdom')
     ) AS v(state_name, country_name)
JOIN countries c ON c.country_name = v.country_name
ON CONFLICT (state_name) DO NOTHING;

-- ── cities (linked to their state by name) ───────────────────────────────────
INSERT INTO cities (city_id, city_name, state)
SELECT nextval('city_id_generator'), v.city, st.state_id
FROM (VALUES
        ('New Delhi',     'Delhi'),
        ('Mumbai',        'Maharashtra'),
        ('Pune',          'Maharashtra'),
        ('Nagpur',        'Maharashtra'),
        ('Bengaluru',     'Karnataka'),
        ('Mysuru',        'Karnataka'),
        ('Chennai',       'Tamil Nadu'),
        ('Coimbatore',    'Tamil Nadu'),
        ('Hyderabad',     'Telangana'),
        ('Warangal',      'Telangana'),
        ('Lucknow',       'Uttar Pradesh'),
        ('Kanpur',        'Uttar Pradesh'),
        ('Noida',         'Uttar Pradesh'),
        ('Kolkata',       'West Bengal'),
        ('Howrah',        'West Bengal'),
        ('Ahmedabad',     'Gujarat'),
        ('Surat',         'Gujarat'),
        ('Jaipur',        'Rajasthan'),
        ('Udaipur',       'Rajasthan'),
        ('Amritsar',      'Punjab'),
        ('Ludhiana',      'Punjab'),
        ('Los Angeles',   'California'),
        ('San Francisco', 'California'),
        ('San Diego',     'California'),
        ('Houston',       'Texas'),
        ('Austin',        'Texas'),
        ('Dallas',        'Texas'),
        ('New York City', 'New York'),
        ('Buffalo',       'New York'),
        ('London',        'England'),
        ('Manchester',    'England')
     ) AS v(city, state_name)
JOIN states st ON st.state_name = v.state_name
ON CONFLICT (city_name) DO NOTHING;
