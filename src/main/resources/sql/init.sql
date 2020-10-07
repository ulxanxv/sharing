INSERT
    INTO credential
        (name, password, role)
    VALUES
        ('Ulxanxv', '$2y$12$.EzBlMBHH9jp5lHgZi4Fw.sEaIeaM2E2G3.h5oE7L8IY2YDUhB/la', 'USER'),
        ('Amberd', '$2y$12$RrVi5cJ94Yo93whKACUzfec2qcVkNOt5QbPyn6eO56VUheLQnw8Fu', 'USER'),
        ('Ynagan', '$2y$12$eE1e7YoLP5CIcNccAHWgLOsnGssGmKH6V32.d.KpfE3DzxYuCV4v2', 'USER');

INSERT
    INTO client
        (name, credential_id)
    VALUES
        ('Ulxanxv', 1),
        ('Amberd', 2),
        ('Ynagan', 3);

INSERT
    INTO disk
       (name, owner_id)
    VALUES
        ('First Disk', 1),
        ('Second Disk', 1),
        ('Third Disk', 1),
        ('Fourth Disk', 2),
        ('Fifth Disk', 2),
        ('Sixth Disk', 3),
        ('Seventh Disk', 3);

INSERT
    INTO taken_item
        (is_free, disk_id, owner_id, debtor_id)
    VALUES
        (true, 1, 1, NULL),
        (true, 2, 1, NULL),
        (true, 3, 1, NULL),
        (true, 4, 2, NULL),
        (true, 5, 2, 2),
        (false , 6, 3, 1),
        (false, 7, 3, 1);
