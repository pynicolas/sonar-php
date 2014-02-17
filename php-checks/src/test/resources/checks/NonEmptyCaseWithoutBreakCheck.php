<?php

switch ($a) {
  case 0:
  case 1:     // NOK
    doSomething();
  case 2:     // NOK
    __halt_compiler();
  default:    // OK
    doSomethingElse();
}

switch ($a) {
  default:     // NOK
    doSomething();
  case 2:      // OK
    doSomethingElse();
}

switch ($a) {
  case 0:      // OK
  case 1:      // OK
    break;
  case 2:      // OK
    return;
  case 3:      // OK
    throw new Exception();
  case 4:      // OK
    continue;
  case 5:      // OK
    exit(0);
  default:     // OK
    break;
}
