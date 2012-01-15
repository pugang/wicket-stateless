wicket-stateless
============
stateless wicket components based of the work of jolira.  orginal implmentation can be found at [wicket-stateless](https://github.com/jolira/wicket-stateless).  i tried upgrading jolira's code but was running into issues with Maven so I decided to copy their code into this version.

changes to orginal jolira implementation
==============================
- updated to Wicket 6.0-Snapshot
- build uses [Gradle](http://gradle.org/) exclusively
- added additional stateless components
	- StatelessAjaxEventBehavior
	- StatelessAjaxButton
- example page integrated with [Twitter Bootstrap](http://twitter.github.com/bootstrap/)
- removed Scala demo