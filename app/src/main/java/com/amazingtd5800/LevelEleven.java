package com.amazingtd5800;

import java.util.List;

/** Level 11 (background 4), starts with 300 money. Original: qj. */
final class LevelEleven extends Level {

    LevelEleven(Hud hud, int number) {
        super(hud, number, 4);
        // Path from entry point 0, 8 waypoints. Original: dd.
        Path path0 = new Path(8);
        addPath(path0);
        path0.add(new Vec2(snap(100), snap(0)));
        path0.add(new Vec2(snap(100), snap(30)));
        path0.add(new Vec2(snap(300), snap(80)));
        path0.add(new Vec2(snap(270), snap(140)));
        path0.add(new Vec2(snap(113), snap(116)));
        path0.add(new Vec2(snap(121), snap(165)));
        path0.add(new Vec2(snap(268), snap(198)));
        path0.add(new Vec2(snap(GameScreen.WIDTH), snap(124)));
        // Path from entry point 1, 7 waypoints. Original: dd.
        Path path1 = new Path(7);
        addPath(path1);
        path1.add(new Vec2(snap(290), snap(0)));
        path1.add(new Vec2(snap(160), snap(230)));
        path1.add(new Vec2(snap(40), snap(180)));
        path1.add(new Vec2(snap(60), snap(240)));
        path1.add(new Vec2(snap(200), snap(284)));
        path1.add(new Vec2(snap(180), snap(370)));
        path1.add(new Vec2(snap(0), snap(400)));
        // Path from entry point 2, 8 waypoints. Original: dd.
        Path path2 = new Path(8);
        addPath(path2);
        path2.add(new Vec2(snap(GameScreen.WIDTH + 10), snap(560)));
        path2.add(new Vec2(snap(GameScreen.WIDTH - 190), snap(430)));
        path2.add(new Vec2(snap(260), snap(340)));
        path2.add(new Vec2(snap(122), snap(313)));
        path2.add(new Vec2(snap(112), snap(421)));
        path2.add(new Vec2(snap(27), snap(506)));
        path2.add(new Vec2(snap(168), snap(511)));
        path2.add(new Vec2(snap(0), snap(583)));
        // 8 enemies every 5000 ms, the first after 30000 ms. Original: bm.
        addWave(new Wave(30000, 8, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
            }
        });
        // 8 enemies every 5000 ms, the first after 20000 ms. Original: br.
        addWave(new Wave(20000, 8, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.SUN_FOUR, false));
            }
        });
        // 15 enemies every 3000 ms, the first after 20000 ms. Original: bt.
        addWave(new Wave(20000, 15, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.BLOB, false));
                waiting.add(spawn(paths[2], EnemyType.BLOB, false));
            }
        });
        // 20 enemies every 4000 ms, the first after 20000 ms. Original: bo.
        addWave(new Wave(20000, 20, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.LINE, true));
            }
        });
        // 16 enemies every 5000 ms, the first after 20000 ms. Original: bp.
        addWave(new Wave(20000, 16, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.TRIANGLE, false));
            }
        });
        // 20 enemies every 3000 ms, the first after 20000 ms. Original: bh.
        addWave(new Wave(20000, 20, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                if (remaining == 20) {
                    waiting.add(spawn(paths[1], EnemyType.CLOCK, false));
                    interval = 6000;
                    return;
                }
                interval = 3000;
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 20 enemies every 3000 ms, the first after 20000 ms. Original: bj.
        addWave(new Wave(20000, 20, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[1], EnemyType.LINE, false));
                waiting.add(spawn(paths[2], EnemyType.GHOST, false));
            }
        });
        // 20 enemies every 3000 ms, the first after 20000 ms. Original: bd.
        addWave(new Wave(20000, 20, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.GHOST, false));
                    waiting.add(spawn(paths[1], EnemyType.LINE_TWO, false));
                    waiting.add(spawn(paths[2], EnemyType.GHOST, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.SUN_THREE, false));
            }
        });
        // 20 enemies every 3400 ms, the first after 20000 ms. Original: bf.
        addWave(new Wave(20000, 20, 3400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[1], EnemyType.TRIANGLE, false));
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE, false));
                waiting.add(spawn(paths[2], EnemyType.TRIANGLE, false));
            }
        });
        // 8 enemies every 4500 ms, the first after 20000 ms. Original: pg.
        addWave(new Wave(20000, 8, 4500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL_TWO, false));
            }
        });
        // 20 enemies every 3000 ms, the first after 20000 ms. Original: pj.
        addWave(new Wave(20000, 20, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
                waiting.add(spawn(paths[1], EnemyType.TRIANGLE, false));
                waiting.add(spawn(paths[2], EnemyType.TRIANGLE, false));
            }
        });
        // 14 enemies every 700 ms, the first after 20000 ms. Original: pi.
        addWave(new Wave(20000, 14, 700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
            }
        });
        // 12 enemies every 1200 ms, the first after 20000 ms. Original: oq.
        addWave(new Wave(20000, 12, 1200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.WORM, false));
            }
        });
        // 26 enemies every 2000 ms, the first after 20000 ms. Original: or.
        addWave(new Wave(20000, 26, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 2);
                waiting.add(spawn(paths[0], EnemyType.VIRUS, false));
                waiting.add(spawn(paths[2], EnemyType.VIRUS, false));
            }
        });
        // 18 enemies every 2800 ms, the first after 20000 ms. Original: oo.
        addWave(new Wave(20000, 18, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
                Enemy enemy = spawn(paths[1], EnemyType.BALL, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 22 enemies every 2800 ms, the first after 20000 ms. Original: op.
        addWave(new Wave(20000, 22, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 2);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, false));
                Enemy enemy = spawn(paths[2], EnemyType.SHREDDER_TWO, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 10 enemies every 6000 ms, the first after 20000 ms. Original: ou.
        addWave(new Wave(20000, 10, 6000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                Enemy enemy = spawn(paths[0], EnemyType.CLOCK, false);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[1], EnemyType.CLOCK, false);
                waiting.add(enemy2);
                Enemy enemy3 = spawn(paths[2], EnemyType.CLOCK, false);
                enemy3.setVisible(false);
                waiting.add(enemy3);
            }
        });
        // 16 enemies every 1000 ms, the first after 20000 ms. Original: ov.
        addWave(new Wave(20000, 16, 1000) {
            /** Waypoint index the wave is walking now. Original: ov.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[index], EnemyType.SPARK, false));
                index = (index + 1) % 3;
                if (index == 0) {
                    interval = 8000;
                    return;
                }
                interval = 1000;
            }
        });
        // 14 enemies every 900 ms, the first after 20000 ms. Original: os.
        addWave(new Wave(20000, 14, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                interval = remaining % 5 == 0 ? 4000 : 900;
                waiting.add(spawn(paths[Randoms.pick(paths.length)], EnemyType.SUN, false));
            }
        });
        // 14 enemies every 1200 ms, the first after 20000 ms. Original: oz.
        addWave(new Wave(20000, 14, 1200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 2);
                waiting.add(spawn(paths[0], EnemyType.WORM, false));
                waiting.add(spawn(paths[2], EnemyType.WORM, false));
            }
        });
        // 10 enemies every 3000 ms, the first after 20000 ms. Original: oy.
        addWave(new Wave(20000, 10, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                waiting.add(spawn(paths[0], EnemyType.SUN_FOUR, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_THREE, false));
                waiting.add(spawn(paths[2], EnemyType.SUN_FOUR, false));
            }
        });
        // 14 enemies every 800 ms, the first after 20000 ms. Original: ox.
        addWave(new Wave(20000, 14, 800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
            }
        });
        // 14 enemies every 800 ms, the first after 20000 ms. Original: ow.
        addWave(new Wave(20000, 14, 800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
            }
        });
        // 14 enemies every 1000 ms, the first after 20000 ms. Original: om.
        addWave(new Wave(20000, 14, 1000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                interval = remaining % 2 == 1 ? 3000 : 1000;
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE, false));
                waiting.add(spawn(paths[1], EnemyType.ELLIPSE_THREE, false));
                waiting.add(spawn(paths[2], EnemyType.ELLIPSE, false));
            }
        });
        hud.start(300, waves());
    }
}
