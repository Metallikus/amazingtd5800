package com.amazingtd5800;

import java.util.List;

/** Level 15 (background 1), starts with 300 money. Original: qe. */
final class LevelFifteen extends Level {

    LevelFifteen(Hud hud, int number) {
        super(hud, number, 1);
        // Path from entry point 0, 8 waypoints. Original: dd.
        Path path0 = new Path(8);
        addPath(path0);
        path0.add(new Vec2(snap(50), snap(0)));
        path0.add(new Vec2(snap(50), snap(500)));
        path0.add(new Vec2(snap(115), snap(570)));
        path0.add(new Vec2(snap(180), snap(500)));
        path0.add(new Vec2(snap(180), snap(100)));
        path0.add(new Vec2(snap(245), snap(30)));
        path0.add(new Vec2(snap(310), snap(100)));
        path0.add(new Vec2(snap(310), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 1, 5 waypoints. Original: dd.
        Path path1 = new Path(5);
        addPath(path1);
        path1.add(new Vec2(snap(-10), snap(400)));
        path1.add(new Vec2(snap(110), snap(540)));
        path1.add(new Vec2(snap(300), snap(340)));
        path1.add(new Vec2(snap(300), snap(108)));
        path1.add(new Vec2(snap(180), snap(-10)));
        // Path from entry point 2, 5 waypoints. Original: dd.
        Path path2 = new Path(5);
        addPath(path2);
        path2.add(new Vec2(snap(GameScreen.WIDTH + 10), snap(200)));
        path2.add(new Vec2(snap(235), snap(70)));
        path2.add(new Vec2(snap(70), snap(220)));
        path2.add(new Vec2(snap(70), snap(460)));
        path2.add(new Vec2(snap(200), snap(GameScreen.HEIGHT - 58 + 10)));
        // 8 enemies every 8000 ms, the first after 30000 ms. Original: kq.
        addWave(new Wave(30000, 8, 8000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
            }
        });
        // 18 enemies every 2600 ms, the first after 20000 ms. Original: jv.
        addWave(new Wave(20000, 18, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN, false));
            }
        });
        // 12 enemies every 2800 ms, the first after 20000 ms. Original: ju.
        addWave(new Wave(20000, 12, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.LINE, false));
            }
        });
        // 12 enemies every 2800 ms, the first after 20000 ms. Original: js.
        addWave(new Wave(20000, 12, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.LINE, false));
            }
        });
        // 15 enemies every 3800 ms, the first after 20000 ms. Original: jq.
        addWave(new Wave(20000, 15, 3800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.TRIANGLE, false));
            }
        });
        // 12 enemies every 5000 ms, the first after 20000 ms. Original: kf.
        addWave(new Wave(20000, 12, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
            }
        });
        // 12 enemies every 2300 ms, the first after 20000 ms. Original: kb.
        addWave(new Wave(20000, 12, 2300) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.LINE, false));
                    waiting.add(spawn(paths[1], EnemyType.LINE, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
                waiting.add(spawn(paths[1], EnemyType.TRIANGLE, false));
            }
        });
        // 15 enemies every 3500 ms, the first after 20000 ms. Original: jz.
        addWave(new Wave(20000, 15, 3500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.GHOST_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.GHOST, true));
                waiting.add(spawn(paths[2], EnemyType.GHOST, true));
            }
        });
        // 16 enemies every 3000 ms, the first after 20000 ms. Original: jx.
        addWave(new Wave(20000, 16, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE_TWO, false));
            }
        });
        // 10 enemies every 3000 ms, the first after 20000 ms. Original: i.
        addWave(new Wave(20000, 10, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.SUN_THREE, false));
                waiting.add(spawn(paths[2], EnemyType.SUN_THREE, false));
            }
        });
        // 10 enemies every 1000 ms, the first after 20000 ms. Original: h.
        addWave(new Wave(20000, 10, 1000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, true));
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[2], EnemyType.BALL_THREE, false));
            }
        });
        // 12 enemies every 4000 ms, the first after 20000 ms. Original: g.
        addWave(new Wave(20000, 12, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                interval = remaining % 2 == 1 ? 4000 : 1000;
                waiting.add(spawn(paths[0], EnemyType.WORM, true));
                waiting.add(spawn(paths[1], EnemyType.WORM, false));
                waiting.add(spawn(paths[2], EnemyType.WORM, false));
            }
        });
        // 14 enemies every 3300 ms, the first after 20000 ms. Original: n.
        addWave(new Wave(20000, 14, 3300) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.VIRUS_TWO, false));
                Enemy enemy = spawn(paths[1], EnemyType.BALL_THREE, true);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[2], EnemyType.BALL_THREE, true);
                waiting.add(enemy2);
            }
        });
        // 10 enemies every 5000 ms, the first after 20000 ms. Original: m.
        addWave(new Wave(20000, 10, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.GHOST_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.VIRUS2, false));
                waiting.add(spawn(paths[2], EnemyType.VIRUS2, false));
            }
        });
        // 16 enemies every 4500 ms, the first after 20000 ms. Original: l.
        addWave(new Wave(20000, 16, 4500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                interval = remaining % 2 == 1 ? 4500 : 900;
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, true));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 16 enemies every 3500 ms, the first after 20000 ms. Original: k.
        addWave(new Wave(20000, 16, 3500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.WORM_TWO, false));
                    waiting.add(spawn(paths[1], EnemyType.CIRCLE_TWO, false));
                    waiting.add(spawn(paths[2], EnemyType.CIRCLE_TWO, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, false));
                waiting.add(spawn(paths[1], EnemyType.WORM, false));
                waiting.add(spawn(paths[2], EnemyType.WORM, false));
            }
        });
        // 12 enemies every 4000 ms, the first after 20000 ms. Original: p.
        addWave(new Wave(20000, 12, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.GHOST_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.GHOST_THREE, true));
                waiting.add(spawn(paths[2], EnemyType.GHOST_THREE, false));
            }
        });
        // 20 enemies every 2300 ms, the first after 20000 ms. Original: r.
        addWave(new Wave(20000, 20, 2300) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, true));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 10 enemies every 1500 ms, the first after 20000 ms. Original: s.
        addWave(new Wave(20000, 10, 1500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 2);
                waiting.add(spawn(paths[0], EnemyType.VIRUS_TWO, false));
                waiting.add(spawn(paths[2], EnemyType.VIRUS_TWO, false));
            }
        });
        // 10 enemies every 3000 ms, the first after 20000 ms. Original: x.
        addWave(new Wave(20000, 10, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.SPARK_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.VIRUS2, false));
                waiting.add(spawn(paths[2], EnemyType.VIRUS2, false));
            }
        });
        // 20 enemies every 3200 ms, the first after 20000 ms. Original: v.
        addWave(new Wave(20000, 20, 3200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                Enemy enemy = spawn(paths[0], EnemyType.GHOST_TWO, false);
                enemy.setVisible(false);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[1], EnemyType.GHOST_TWO, false);
                enemy2.setVisible(false);
                waiting.add(enemy2);
                Enemy enemy3 = spawn(paths[2], EnemyType.GHOST_TWO, false);
                enemy3.setVisible(false);
                waiting.add(enemy3);
            }
        });
        hud.start(300, waves());
    }
}
