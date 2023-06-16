# lab7
The task for this lab: add multithreading on server side, keep elements in db,make server collection thread save.
## add multithreading
In lab 6 server was single threaded, so it could accept only one connection, other clients had to wait. 
Now there is a main thread, which accepts connection and submit task for processing request to other threads.
I used Fixed thread pool for submitting tasks.
## keep elements in db
In lab 6 elements were saved in xml file.
Before accepting connections, server parsed file.Now elements are saved in db.
I used postgresql driver for interacting for db.
I intentionally refused to work with library hibernate from the very beginning.
Using driver helped me to understand what happens under the hood.
## make server collection thread save
All elements are kept in ArrayList.
According to task, I  didn’t change collection on thread save.
I tried to make my initial collection saved.I learned different synchronization,locks.
I used ReadWriteLock to make save all critical zone.The problem also was in visibility.
I understood that static elements are evil.
