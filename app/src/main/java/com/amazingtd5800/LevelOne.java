package com.amazingtd5800;

import java.util.List;

/** Level 1 (background 1), starts with 120 money. Original: gb. */
final class LevelOne extends Level {

    LevelOne(Hud hud, int number) {
        super(hud, number, 1);
        // Path from entry point 0, 12 waypoints. Original: dd.
        Path path0 = new Path(12);
        addPath(path0);
        path0.add(new Vec2(snap(0), snap(100)));
        path0.add(new Vec2(snap(250), snap(100)));
        path0.add(new Vec2(snap(250), snap(160)));
        path0.add(new Vec2(snap(60), snap(160)));
        path0.add(new Vec2(snap(60), snap(300)));
        path0.add(new Vec2(snap(130), snap(300)));
        path0.add(new Vec2(snap(130), snap(360)));
        path0.add(new Vec2(snap(60), snap(360)));
        path0.add(new Vec2(snap(60), snap(460)));
        path0.add(new Vec2(snap(200), snap(460)));
        path0.add(new Vec2(snap(200), snap(230)));
        path0.add(new Vec2(snap(360), snap(230)));
        // 10 enemies every 4000 ms, the first after 30000 ms. Original: iu.
        addWave(new Wave(30000, 10, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
            }
        });
        // 6 enemies every 3400 ms, the first after 20000 ms. Original: it.
        addWave(new Wave(20000, 6, 3400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
            }
        });
        // 15 enemies every 3000 ms, the first after 20000 ms. Original: is.
        addWave(new Wave(20000, 15, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
            }
        });
        // 6 enemies every 2800 ms, the first after 20000 ms. Original: ir.
        addWave(new Wave(20000, 6, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, false));
            }
        });
        // 8 enemies every 2600 ms, the first after 20000 ms. Original: iq.
        addWave(new Wave(20000, 8, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL, false));
            }
        });
        // 18 enemies every 1800 ms, the first after 20000 ms. Original: ip.
        addWave(new Wave(20000, 18, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
            }
        });
        // 10 enemies every 2500 ms, the first after 20000 ms. Original: in.
        addWave(new Wave(20000, 10, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE, false));
            }
        });
        // 10 enemies every 2400 ms, the first after 20000 ms. Original: im.
        addWave(new Wave(20000, 10, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, false));
            }
        });
        // 12 enemies every 2500 ms, the first after 20000 ms. Original: ia.
        addWave(new Wave(20000, 12, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN, false));
            }
        });
        // 14 enemies every 1900 ms, the first after 20000 ms. Original: ax.
        addWave(new Wave(20000, 14, 1900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL, false));
            }
        });
        // 18 enemies every 2600 ms, the first after 20000 ms. Original: az.
        addWave(new Wave(20000, 18, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
            }
        });
        // 15 enemies every 1800 ms, the first after 20000 ms. Original: av.
        addWave(new Wave(20000, 15, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, true));
            }
        });
        // 16 enemies every 2100 ms, the first after 20000 ms. Original: aw.
        addWave(new Wave(20000, 16, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN, false));
            }
        });
        // 16 enemies every 2200 ms, the first after 20000 ms. Original: ap.
        addWave(new Wave(20000, 16, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 10 enemies every 1800 ms, the first after 20000 ms. Original: as.
        addWave(new Wave(20000, 10, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN_THREE, false));
            }
        });
        // 16 enemies every 2300 ms, the first after 20000 ms. Original: aj.
        addWave(new Wave(20000, 16, 2300) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE_TWO, false));
            }
        });
        // 24 enemies every 1400 ms, the first after 20000 ms. Original: am.
        addWave(new Wave(20000, 24, 1400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 1 enemies every 0 ms, the first after 20000 ms. Original: ds.
        addWave(new Wave(20000, 1, 0) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CLOCK, false));
            }
        });
        hud.start(120, waves());
    }
}
