# SharePool Server

## Description

Have a look at the initial app [proposal](PROPOSAL.md) to see what it is all about.

&copy; Tobias Kaderle & Jan Wiesbauer

## Analytics Service
Ein weiterer Punkt, den wir in unsere App anbieten wollten, ist die Ansicht von Statistiken und Analyse-Daten. Im Grund soll der Benutzer sehen wieviele Fahrten ehr absolviert hat, und wieviel Treibstoff durch das gemeinsame Fahren eingespart wurde. Da diese Funktionalität gut von den Hautpfunktionalitäten (Tour-Verwaltung, Fahrten-Buchung, ...) entkoppelt werden kann, lagerten wir diese in einen weiteren Microservice aus.

Der Service ist auch mit Spring-Boot implementiert, und empfängt über RabbitMQ Informationen über neue gebuchte Fahrten. Wir haben uns für RabbitMQ entscheiden, da bei einer frequenten Nutzung des `Server-Services` es zu keiner Blockade kommt, wenn der `Analytics Service` nicht mit der Ababrbeitung nachkommt. Die Nachrichten häufen sich einfach im Broker an und werden dann nach und nach abgearbeitet.

Über das Frontend kann ein User dann seine Analyse-Daten abfragen. Da diese pro Tag gleich aufsumiert angezeigt werden sollen, wird diese Transformation gleich am Server vollzogen. So wird an das Frontend eine `Map` mit Datum als Schlüssel gesendet, die als Werte die Summen der Fahrten und des gesparten Treibstoffs hat. So können die Daten direkt so in der UI angezeigt werde, ohne groß umgerechnet zu werden.