# remind-warrior
remind-warrior reads [TaskWarrior's](https://taskwarrior.org/) tasks, filters
and sorts them, then converts them to the
[Remind](https://www.roaringpenguin.com/products/remind) format. This allows me
to use [Wyrd](http://pessimization.com/software/wyrd/) to see all of my TODOs
and due dates, as well as my normal reminders.

## Build
```bash
$ lein uberjar
```

## Usage
Add the following line to your `~/.reminders`, adjusting as needed:
```remind
INCLUDE /home/USER/.remind/remind-warrior.rem
```

Now add a job to your [crontab](https://wiki.archlinux.org/index.php/Cron). In
this example, we update every 5 minutes:
```bash
$ crontab -e
*/5 * * * * java -jar ~/projects/remind-warrior/target/uberjar/remind-warrior-0.1.0-standalone.jar >| ~/.remind/remind-warrior.rem
```
