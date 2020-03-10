package com.example.exception

import com.example.service.PropertyViolation

// NOTE: property のエラーは複数をまとめたいケースが多いと思うので
class InvalidPropertyException(val violations: List[PropertyViolation])
  extends ValidationException(violations.toString())
