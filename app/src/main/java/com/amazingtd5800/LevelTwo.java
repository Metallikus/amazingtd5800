package com.amazingtd5800;

import java.util.List;

/** Level 2 (background 2), starts with 100 money. Original: fv. */
final class LevelTwo extends Level {

    LevelTwo(Hud hud, int number) {
        super(hud, number, 2);
        // Path from entry point 0, 11 waypoints. Original: dd.
        Path path0 = new Path(11);
        addPath(path0);
        path0.add(new Vec2(snap(292), snap(0)));
        path0.add(new Vec2(snap(292), snap(132)));
        path0.add(new Vec2(snap(160), snap(132)));
        path0.add(new Vec2(snap(160), snap(90)));
        path0.add(new Vec2(snap(40), snap(90)));
        path0.add(new Vec2(snap(40), snap(280)));
        path0.add(new Vec2(snap(260), snap(280)));
        path0.add(new Vec2(snap(260), snap(180)));
        path0.add(new Vec2(snap(160), snap(180)));
        path0.add(new Vec2(snap(160), snap(330)));
        path0.add(new Vec2(snap(0), snap(330)));
        // 6 enemies every 2800 ms, the first after 30000 ms. Original: fl.
        addWave(new Wave(30000, 6, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, false));
            }
        });
        // 8 enemies every 3000 ms, the first after 20000 ms. Original: fj.
        addWave(new Wave(20000, 8, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
            }
        });
        // 14 enemies every 2500 ms, the first after 20000 ms. Original: fk.
        addWave(new Wave(20000, 14, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
            }
        });
        // 12 enemies every 2500 ms, the first after 20000 ms. Original: fr.
        addWave(new Wave(20000, 12, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, false));
            }
        });
        // 10 enemies every 2800 ms, the first after 20000 ms. Original: ft.
        addWave(new Wave(20000, 10, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
            }
        });
        // 16 enemies every 2000 ms, the first after 20000 ms. Original: fm.
        addWave(new Wave(20000, 16, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE, false));
                if (remaining % 4 == 1) {
                    interval = 4000;
                    return;
                }
                interval = 1000;
            }
        });
        // 14 enemies every 2400 ms, the first after 20000 ms. Original: fo.
        addWave(new Wave(20000, 14, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN_THREE, false));
            }
        });
        // 15 enemies every 2000 ms, the first after 20000 ms. Original: id.
        addWave(new Wave(20000, 15, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST, true));
            }
        });
        // 10 enemies every 2200 ms, the first after 20000 ms. Original: ie.
        addWave(new Wave(20000, 10, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, true));
            }
        });
        // 16 enemies every 2000 ms, the first after 20000 ms. Original: mo.
        addWave(new Wave(20000, 16, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, 25, 0.034f, 1, false));
                if (remaining % 4 == 1) {
                    interval = 2000;
                    return;
                }
                interval = 800;
            }
        });
        // 16 enemies every 2000 ms, the first after 20000 ms. Original: mm.
        addWave(new Wave(20000, 16, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE, true));
            }
        });
        // 20 enemies every 1800 ms, the first after 20000 ms. Original: mn.
        addWave(new Wave(20000, 20, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, true));
            }
        });
        // 18 enemies every 4000 ms, the first after 20000 ms. Original: mk.
        addWave(new Wave(20000, 18, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
                if (remaining % 3 == 1) {
                    interval = 4000;
                    return;
                }
                interval = 1000;
            }
        });
        // 15 enemies every 2200 ms, the first after 20000 ms. Original: ml.
        addWave(new Wave(20000, 15, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN_THREE, false));
            }
        });
        // 18 enemies every 2000 ms, the first after 20000 ms. Original: mh.
        addWave(new Wave(20000, 18, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
            }
        });
        // 12 enemies every 2200 ms, the first after 20000 ms. Original: mj.
        addWave(new Wave(20000, 12, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, true));
            }
        });
        // 10 enemies every 1400 ms, the first after 20000 ms. Original: md.
        addWave(new Wave(20000, 10, 1400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
            }
        });
        // 20 enemies every 2000 ms, the first after 20000 ms. Original: mf.
        addWave(new Wave(20000, 20, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE, false));
                if (remaining % 2 == 1) {
                    interval = 2000;
                    return;
                }
                interval = 800;
            }
        });
        // 5 enemies every 5500 ms, the first after 20000 ms. Original: mv.
        addWave(new Wave(20000, 5, 5500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CLOCK, false));
            }
        });
        hud.start(100, waves());
    }
}
