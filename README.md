# CachingService

This project contains the implementation of an recruitment exercise from
free2move. The details about the exercise can be found
[here](https://github.com/tGrottker/free2move/blob/master/task.txt).

### Starting the Service

The service is started with `sbt run`.
Once it is started, it will log a message similar to this one:

```
INFO: Finagle version 6.42.0 (rev=f48520b6809792d8cb87c5d81a13075fd01c051d) built at 20170203-165908
```

Now you can call the service with:

```bash
curl http://localhost:8080/INDEX
```

Where `INDEX` is the index of the element you want to query.

### Configuring the Service

If you want to change the refreshment interval, just change the value of
`interval` in the `application.conf` file. This file can be found at
`src/main/resources/application.conf`.
