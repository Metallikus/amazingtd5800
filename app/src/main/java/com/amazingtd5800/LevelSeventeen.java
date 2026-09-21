package com.amazingtd5800;

import java.util.List;

/** Level 17 (background 5), starts with 300 money. Original: qc. */
final class LevelSeventeen extends Level {

    LevelSeventeen(Hud hud, int number) {
        super(hud, number, 5);
        // Path from entry point 0, 7 waypoints. Original: dd.
        Path path0 = new Path(7);
        addPath(path0);
        path0.add(new Vec2(snap(0), snap(180)));
        path0.add(new Vec2(snap(30), snap(180)));
        path0.add(new Vec2(snap(30), snap(260)));
        path0.add(new Vec2(snap(60), snap(260)));
        path0.add(new Vec2(snap(60), snap(530)));
        path0.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(530)));
        path0.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(410)));
        // Path from entry point 1, 8 waypoints. Original: dd.
        Path path1 = new Path(8);
        addPath(path1);
        path1.add(new Vec2(snap(72), snap(0)));
        path1.add(new Vec2(snap(72), snap(120)));
        path1.add(new Vec2(snap(120), snap(120)));
        path1.add(new Vec2(snap(120), snap(260)));
        path1.add(new Vec2(snap(60), snap(260)));
        path1.add(new Vec2(snap(60), snap(530)));
        path1.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(530)));
        path1.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(410)));
        // Path from entry point 2, 8 waypoints. Original: dd.
        Path path2 = new Path(8);
        addPath(path2);
        path2.add(new Vec2(snap(152), snap(0)));
        path2.add(new Vec2(snap(152), snap(120)));
        path2.add(new Vec2(snap(120), snap(120)));
        path2.add(new Vec2(snap(120), snap(260)));
        path2.add(new Vec2(snap(60), snap(260)));
        path2.add(new Vec2(snap(60), snap(530)));
        path2.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(530)));
        path2.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(410)));
        // Path from entry point 3, 8 waypoints. Original: dd.
        Path path3 = new Path(8);
        addPath(path3);
        path3.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(0)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(120)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 120), snap(120)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 120), snap(260)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 60), snap(260)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 60), snap(530)));
        path3.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(530)));
        path3.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(410)));
        // Path from entry point 4, 8 waypoints. Original: dd.
        Path path4 = new Path(8);
        addPath(path4);
        path4.add(new Vec2(snap(GameScreen.WIDTH - 72), snap(0)));
        path4.add(new Vec2(snap(GameScreen.WIDTH - 72), snap(120)));
        path4.add(new Vec2(snap(GameScreen.WIDTH - 120), snap(120)));
        path4.add(new Vec2(snap(GameScreen.WIDTH - 120), snap(260)));
        path4.add(new Vec2(snap(GameScreen.WIDTH - 60), snap(260)));
        path4.add(new Vec2(snap(GameScreen.WIDTH - 60), snap(530)));
        path4.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(530)));
        path4.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(410)));
        // Path from entry point 5, 7 waypoints. Original: dd.
        Path path5 = new Path(7);
        addPath(path5);
        path5.add(new Vec2(snap(GameScreen.WIDTH), snap(180)));
        path5.add(new Vec2(snap(GameScreen.WIDTH - 30), snap(180)));
        path5.add(new Vec2(snap(GameScreen.WIDTH - 30), snap(260)));
        path5.add(new Vec2(snap(GameScreen.WIDTH - 60), snap(260)));
        path5.add(new Vec2(snap(GameScreen.WIDTH - 60), snap(530)));
        path5.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(530)));
        path5.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(410)));
        // Where a tower cannot be placed. Original: cb.
        blocked(snap(GameScreen.WIDTH / 2 - 74), snap(350), snap(GameScreen.WIDTH / 2 + 66), snap(520));
        // Upgrade spot (RELOAD). Original: ol.
        spot(snap(GameScreen.WIDTH / 2 - 32 - 12), snap(368), Spot.RELOAD);
        // Upgrade spot (RANGE). Original: ol.
        spot(snap(GameScreen.WIDTH / 2 + 24 - 12), snap(368), Spot.RANGE);
        // Upgrade spot (RANGE). Original: ol.
        spot(snap(GameScreen.WIDTH / 2 - 48 - 12), snap(432), Spot.RANGE);
        // Upgrade spot (DAMAGE). Original: ol.
        spot(snap(GameScreen.WIDTH / 2 + 40 - 12), snap(432), Spot.DAMAGE);
        // Upgrade spot (RELOAD). Original: ol.
        spot(snap(GameScreen.WIDTH / 2 - 32 - 12), snap(488), Spot.RELOAD);
        // Upgrade spot (DAMAGE). Original: ol.
        spot(snap(GameScreen.WIDTH / 2 + 24 - 12), snap(488), Spot.DAMAGE);
        // Upgrade spot (RELOAD). Original: ol.
        spot(snap(GameScreen.WIDTH / 2 - 12), snap(152), Spot.RELOAD);
        // 30 enemies every 2200 ms, the first after 30000 ms. Original: li.
        addWave(new Wave(30000, 30, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2);
                if (remaining % 2 == 0) {
                    waiting.add(spawn(paths[1], EnemyType.SUN, false));
                    return;
                }
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
            }
        });
        // 20 enemies every 2000 ms, the first after 20000 ms. Original: lf.
        addWave(new Wave(20000, 20, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.BALL, false));
            }
        });
        // 20 enemies every 1800 ms, the first after 20000 ms. Original: lg.
        addWave(new Wave(20000, 20, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.BALL_THREE, false);
                enemy.addEffect(new Heal());
                waiting.add(enemy);
            }
        });
        // 20 enemies every 2500 ms, the first after 20000 ms. Original: ll.
        addWave(new Wave(20000, 20, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.BALL, false));
            }
        });
        // 26 enemies every 1900 ms, the first after 20000 ms. Original: lm.
        addWave(new Wave(20000, 26, 1900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                if (remaining % 3 == 0) {
                    waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                    return;
                }
                if (remaining % 3 == 1) {
                    waiting.add(spawn(paths[1], EnemyType.BALL, false));
                    return;
                }
                waiting.add(spawn(paths[2], EnemyType.SUN_FOUR, false));
            }
        });
        // 12 enemies every 1900 ms, the first after 20000 ms. Original: lj.
        addWave(new Wave(20000, 12, 1900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2);
                if (remaining % 3 == 0) {
                    waiting.add(spawn(paths[0], EnemyType.BLOB, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.BLOB, false));
                waiting.add(spawn(paths[2], EnemyType.BLOB, false));
            }
        });
        // 10 enemies every 1900 ms, the first after 20000 ms. Original: lk.
        addWave(new Wave(20000, 10, 1900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.ELLIPSE_THREE, false));
            }
        });
        // 15 enemies every 3000 ms, the first after 20000 ms. Original: lq.
        addWave(new Wave(20000, 15, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(3);
                waiting.add(spawn(paths[3], EnemyType.GHOST_TWO, false));
            }
        });
        // 26 enemies every 3000 ms, the first after 20000 ms. Original: lo.
        addWave(new Wave(20000, 26, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(3, 4, 5);
                if (remaining % 3 == 0) {
                    waiting.add(spawn(paths[5], EnemyType.BALL, false));
                    return;
                }
                if (remaining % 3 == 1) {
                    waiting.add(spawn(paths[4], EnemyType.SUN_FOUR, false));
                    return;
                }
                waiting.add(spawn(paths[3], EnemyType.BALL_THREE, false));
            }
        });
        // 15 enemies every 1100 ms, the first after 20000 ms. Original: gv.
        addWave(new Wave(20000, 15, 1100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                if (remaining % 2 == 0) {
                    waiting.add(spawn(paths[2], EnemyType.GHOST_TWO, false));
                    return;
                }
                waiting.add(spawn(paths[3], EnemyType.GHOST_TWO, false));
            }
        });
        // 12 enemies every 2500 ms, the first after 20000 ms. Original: hn.
        addWave(new Wave(20000, 12, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2, 3, 4);
                if (remaining % 2 == 0) {
                    waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                    waiting.add(spawn(paths[4], EnemyType.SHREDDER, false));
                    return;
                }
                waiting.add(spawn(paths[2], EnemyType.SUN_FOUR, false));
                waiting.add(spawn(paths[3], EnemyType.SUN_FOUR, false));
            }
        });
        // 20 enemies every 2000 ms, the first after 20000 ms. Original: hp.
        addWave(new Wave(20000, 20, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 4, 5);
                if (remaining % 4 == 0) {
                    waiting.add(spawn(paths[0], EnemyType.TRIANGLE_TWO, false));
                    waiting.add(spawn(paths[5], EnemyType.TRIANGLE_TWO, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, true));
                waiting.add(spawn(paths[4], EnemyType.BALL_THREE, true));
            }
        });
        // 14 enemies every 3000 ms, the first after 20000 ms. Original: hi.
        addWave(new Wave(20000, 14, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2, 3, 4);
                waiting.add(spawn(paths[1], EnemyType.GHOST_TWO, false));
                waiting.add(spawn(paths[2], EnemyType.GHOST_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.GHOST_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.GHOST_TWO, false));
            }
        });
        // 18 enemies every 2700 ms, the first after 20000 ms. Original: hk.
        addWave(new Wave(20000, 18, 2700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 5);
                Enemy enemy = spawn(paths[0], EnemyType.VIRUS2, false);
                enemy.addEffect(new Heal());
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[5], EnemyType.VIRUS2, false);
                enemy2.addEffect(new Heal());
                waiting.add(enemy2);
            }
        });
        // 10 enemies every 4000 ms, the first after 20000 ms. Original: he.
        addWave(new Wave(20000, 10, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4, 5);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[5], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 10 enemies every 3500 ms, the first after 20000 ms. Original: hg.
        addWave(new Wave(20000, 10, 3500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4, 5);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[2], EnemyType.BALL_THREE, true));
                waiting.add(spawn(paths[3], EnemyType.BALL_THREE, true));
                waiting.add(spawn(paths[4], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[5], EnemyType.BALL_THREE, false));
            }
        });
        // 30 enemies every 3600 ms, the first after 20000 ms. Original: ha.
        addWave(new Wave(20000, 30, 3600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4, 5);
                if (remaining % 3 == 0) {
                    waiting.add(spawn(paths[1], EnemyType.GHOST_TWO, false));
                    waiting.add(spawn(paths[2], EnemyType.GHOST_TWO, false));
                }
                if (remaining % 3 == 1) {
                    waiting.add(spawn(paths[3], EnemyType.GHOST_TWO, false));
                    waiting.add(spawn(paths[4], EnemyType.GHOST_TWO, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.BALL_TWO, true));
                waiting.add(spawn(paths[5], EnemyType.BALL_TWO, true));
            }
        });
        // 14 enemies every 7000 ms, the first after 20000 ms. Original: hc.
        addWave(new Wave(20000, 14, 7000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2, 3, 4);
                waiting.add(spawn(paths[1], EnemyType.BALL_TWO, false));
                waiting.add(spawn(paths[2], EnemyType.BALL_TWO, true));
                waiting.add(spawn(paths[3], EnemyType.BALL_TWO, true));
                waiting.add(spawn(paths[4], EnemyType.BALL_TWO, false));
            }
        });
        // 15 enemies every 4300 ms, the first after 20000 ms. Original: ic.
        addWave(new Wave(20000, 15, 4300) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4, 5);
                waiting.add(spawn(paths[0], EnemyType.GHOST_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, true));
                waiting.add(spawn(paths[2], EnemyType.WORM_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.WORM_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.BALL_THREE, true));
                waiting.add(spawn(paths[5], EnemyType.GHOST_TWO, false));
            }
        });
        // 2 enemies every 30000 ms, the first after 20000 ms. Original: hq.
        addWave(new Wave(20000, 2, 30000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 4);
                waiting.add(spawn(paths[1], EnemyType.VIRUS_BIG, false));
                waiting.add(spawn(paths[4], EnemyType.VIRUS_BIG, false));
            }
        });
        // 10 enemies every 4500 ms, the first after 20000 ms. Original: hr.
        addWave(new Wave(20000, 10, 4500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4, 5);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.BALL_TWO, false));
                if (remaining % 2 == 0) {
                    waiting.add(spawn(paths[2], EnemyType.CLOCK_TWO, false));
                    waiting.add(spawn(paths[3], EnemyType.CLOCK_TWO, false));
                }
                waiting.add(spawn(paths[4], EnemyType.BALL_TWO, false));
                waiting.add(spawn(paths[5], EnemyType.SHREDDER_TWO, false));
            }
        });
        hud.start(300, waves());
    }
}
