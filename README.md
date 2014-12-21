
Sedna
=====

This is the first prototype towards creating a new SEDA based wire-frame which
can be extended to the create various even driven application.
Essential part of any staged event driven architecture is stage which will be the
building block of our application. We will end up extending abstract stage with various
functionality as per our requirement.

Stage
------

A stage is essentially a self-sufficient and the smallest independently functional
unit of seda. It has an input and output both of which are being done via a message queue.
Essentially within a stage we will read events from inbound queue, and will write to an
outbound queue, subscribers of which will then process the events as and when they are emitted
by the current stage.

StageController
---------------
A stage controller controls the functioning of the stage and handles the thread pool size,
(to maximize the throughput of the stage and to minimize the latency.)

Event Handler
---------------
The final and the most essential part of any stage is the event
handler which will hold the event processing logic.


A typical stage schematics looks like this.
![Schematics of a Sedna Stage](http://i60.tinypic.com/169kx91.png "A sedna Stage")

Contributors
-------------
* Rohit Kalhans


