# remind-warrior
remind-warrior reads TaskWarrior's tasks, filters and sorts them, and converts
them to the Remind format.

## Build
```bash
$ lein uberjar
```

## Usage
Add the following line to your `~/.reminders`, adjusting as needed:
```remind
INCLUDE /home/USER/.remind/remind-warrior.rem
```

Now add a job to your crontab:
```bash
$ crontab -e

*/5 * * * * java -jar ~/projects/remind-warrior/target/uberjar/remind-warrior-0.1.0-standalone.jar >| ~/.remind/remind-warrior.rem
```
