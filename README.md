wicket-stateless
============



stateless wicket components based of the work of jolira.  orginal implmentation can be found at [wicket-stateless](https://github.com/jolira/wicket-stateless).  i tried upgrading jolira's code but was running into issues with Maven so I decided to copy their code into this version.

live **demo** at [Heroku](http://wicket-stateless.herokuapp.com/)

changes to orginal jolira implementation
==============================
- updated to [Wicket Custom 6.0 Snapshot](https://github.com/robmcguinness/wicket)
- build uses [Gradle](http://gradle.org/) exclusively
- added additional stateless components
	- StatelessAjaxEventBehavior
	- StatelessAjaxButton
- example page integrated with [Twitter Bootstrap v2.0](http://twitter.github.com/bootstrap/)
- removed Scala demo