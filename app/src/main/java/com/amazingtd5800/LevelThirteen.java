package com.amazingtd5800;

import java.util.List;

/** Level 13 (background 2), starts with 300 money. Original: qh. */
final class LevelThirteen extends Level {

    LevelThirteen(Hud hud, int number) {
        super(hud, number, 2);
        // Path from entry point 0, 8 waypoints. Original: dd.
        Path path0 = new Path(8);
        addPath(path0);
        path0.add(new Vec2(snap(0), snap(GameScreen.HEIGHT - 58 - 50)));
        path0.add(new Vec2(snap(50), snap(GameScreen.HEIGHT - 58 - 50)));
        path0.add(new Vec2(snap(50), snap(310)));
        path0.add(new Vec2(snap(150), snap(310)));
        path0.add(new Vec2(snap(150), snap(260)));
        path0.add(new Vec2(snap(50), snap(260)));
        path0.add(new Vec2(snap(50), snap(50)));
        path0.add(new Vec2(snap(0), snap(50)));
        // Path from entry point 1, 6 waypoints. Original: dd.
        Path path1 = new Path(6);
        addPath(path1);
        path1.add(new Vec2(snap(150), snap(0)));
        path1.add(new Vec2(snap(150), snap(200)));
        path1.add(new Vec2(snap(100), snap(200)));
        path1.add(new Vec2(snap(100), snap(370)));
        path1.add(new Vec2(snap(150), snap(370)));
        path1.add(new Vec2(snap(150), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 2, 6 waypoints. Original: dd.
        Path path2 = new Path(6);
        addPath(path2);
        path2.add(new Vec2(snap(GameScreen.WIDTH - 150), snap(0)));
        path2.add(new Vec2(snap(GameScreen.WIDTH - 150), snap(200)));
        path2.add(new Vec2(snap(GameScreen.WIDTH - 96), snap(200)));
        path2.add(new Vec2(snap(GameScreen.WIDTH - 96), snap(370)));
        path2.add(new Vec2(snap(GameScreen.WIDTH - 150), snap(370)));
        path2.add(new Vec2(snap(GameScreen.WIDTH - 150), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 3, 8 waypoints. Original: dd.
        Path path3 = new Path(8);
        addPath(path3);
        path3.add(new Vec2(snap(GameScreen.WIDTH), snap(GameScreen.HEIGHT - 58 - 50)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 50), snap(GameScreen.HEIGHT - 58 - 50)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 50), snap(310)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 150), snap(310)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 150), snap(260)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 50), snap(260)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 50), snap(50)));
        path3.add(new Vec2(snap(GameScreen.WIDTH), snap(50)));
        // 8 enemies every 3000 ms, the first after 30000 ms. Original: ag.
        addWave(new Wave(30000, 8, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.BLOB, false));
                waiting.add(spawn(paths[2], EnemyType.BLOB, false));
            }
        });
        // 14 enemies every 2700 ms, the first after 20000 ms. Original: ae.
        addWave(new Wave(20000, 14, 2700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
            }
        });
        // 16 enemies every 2500 ms, the first after 20000 ms. Original: ac.
        addWave(new Wave(20000, 16, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.BLOB, false));
                waiting.add(spawn(paths[2], EnemyType.BLOB, false));
            }
        });
        // 16 enemies every 3000 ms, the first after 20000 ms. Original: au.
        addWave(new Wave(20000, 16, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                interval = remaining % 3 == 1 ? 800 : 4000;
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[2], EnemyType.BALL_THREE, false));
            }
        });
        // 8 enemies every 4000 ms, the first after 20000 ms. Original: aq.
        addWave(new Wave(20000, 8, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 3);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, false));
                waiting.add(spawn(paths[3], EnemyType.CIRCLE, false));
            }
        });
        // 20 enemies every 2400 ms, the first after 20000 ms. Original: an.
        addWave(new Wave(20000, 20, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.BLOB, false));
                waiting.add(spawn(paths[3], EnemyType.BLOB, false));
            }
        });
        // 8 enemies every 3000 ms, the first after 20000 ms. Original: ak.
        addWave(new Wave(20000, 8, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.CIRCLE, false));
                waiting.add(spawn(paths[2], EnemyType.CIRCLE, false));
            }
        });
        // 20 enemies every 2400 ms, the first after 20000 ms. Original: ba.
        addWave(new Wave(20000, 20, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 2);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[2], EnemyType.BALL, false));
            }
        });
        // 20 enemies every 2000 ms, the first after 20000 ms. Original: ay.
        addWave(new Wave(20000, 20, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                if (remaining % 3 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.VIRUS, false));
                    waiting.add(spawn(paths[3], EnemyType.VIRUS, false));
                    return;
                }
                Enemy enemy = spawn(paths[1], EnemyType.SUN_THREE, false);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[2], EnemyType.SUN_THREE, false);
                waiting.add(enemy2);
            }
        });
        // 8 enemies every 1800 ms, the first after 20000 ms. Original: ns.
        addWave(new Wave(20000, 8, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
            }
        });
        // 10 enemies every 2600 ms, the first after 20000 ms. Original: nn.
        addWave(new Wave(20000, 10, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.GHOST, true));
                waiting.add(spawn(paths[2], EnemyType.GHOST, true));
            }
        });
        // 10 enemies every 2200 ms, the first after 20000 ms. Original: no.
        addWave(new Wave(20000, 10, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 3);
                waiting.add(spawn(paths[0], EnemyType.SUN_THREE, true));
                waiting.add(spawn(paths[3], EnemyType.SUN_THREE, true));
            }
        });
        // 10 enemies every 2800 ms, the first after 20000 ms. Original: np.
        addWave(new Wave(20000, 10, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, true));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, true));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER, false));
            }
        });
        // 14 enemies every 2500 ms, the first after 20000 ms. Original: nq.
        addWave(new Wave(20000, 14, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.SUN, false));
                    waiting.add(spawn(paths[3], EnemyType.SUN, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.SUN, false));
                waiting.add(spawn(paths[1], EnemyType.GHOST, false));
                waiting.add(spawn(paths[2], EnemyType.GHOST, false));
                waiting.add(spawn(paths[3], EnemyType.SUN, false));
            }
        });
        // 20 enemies every 2600 ms, the first after 20000 ms. Original: nm.
        addWave(new Wave(20000, 20, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.BALL, false));
                Enemy enemy = spawn(paths[1], EnemyType.BALL_THREE, false);
                enemy.setVisible(false);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[2], EnemyType.BALL, false);
                enemy2.setVisible(false);
                waiting.add(enemy2);
                waiting.add(spawn(paths[3], EnemyType.BALL_THREE, false));
            }
        });
        // 4 enemies every 3000 ms, the first after 20000 ms. Original: nk.
        addWave(new Wave(20000, 4, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.SPARK, false));
                waiting.add(spawn(paths[2], EnemyType.SUN_THREE, false));
            }
        });
        // 30 enemies every 1700 ms, the first after 20000 ms. Original: nh.
        addWave(new Wave(20000, 30, 1700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 3);
                waiting.add(spawn(paths[0], EnemyType.SUN, false));
                waiting.add(spawn(paths[3], EnemyType.SUN, false));
                interval -= 10;
            }
        });
        hud.start(300, waves());
    }
}
