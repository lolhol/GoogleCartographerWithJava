
include "map_builder.lua"
include "trajectory_builder.lua"

options = {
  map_builder = MAP_BUILDER,
  trajectory_builder = TRAJECTORY_BUILDER,
}

TRAJECTORY_BUILDER.trajectory_builder_2d.use_imu_data = false
TRAJECTORY_BUILDER_2D.num_accumulated_range_data = 1

return options
