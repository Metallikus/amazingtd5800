package com.amazingtd5800;

import java.util.List;

/** Level 9 (background 5), starts with 200 money. Original: fs. */
final class LevelNine extends Level {

    LevelNine(Hud hud, int number) {
        super(hud, number, 5);
        // Path from entry point 0, 9 waypoints. Original: dd.
        Path path0 = new Path(9);
        addPath(path0);
        path0.add(new Vec2(snap(-10), snap(130)));
        path0.add(new Vec2(snap(24), snap(154)));
        path0.add(new Vec2(snap(60), snap(128)));
        path0.add(new Vec2(snap(66), snap(160)));
        path0.add(new Vec2(snap(80), snap(168)));
        path0.add(new Vec2(snap(112), snap(208)));
        path0.add(new Vec2(snap(128), snap(251)));
        path0.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(302)));
        path0.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 1, 9 waypoints. Original: dd.
        Path path1 = new Path(9);
        addPath(path1);
        path1.add(new Vec2(snap(GameScreen.WIDTH + 20), snap(110)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 40), snap(140)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 52), snap(130)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 74), snap(121)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 98), snap(158)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 122), snap(180)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 134), snap(240)));
        path1.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(260)));
        path1.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 2, 9 waypoints. Original: dd.
        Path path2 = new Path(9);
        addPath(path2);
        path2.add(new Vec2(snap(-10), snap(60)));
        path2.add(new Vec2(snap(80), snap(80)));
        path2.add(new Vec2(snap(100), snap(110)));
        path2.add(new Vec2(snap(124), snap(125)));
        path2.add(new Vec2(snap(128), snap(154)));
        path2.add(new Vec2(snap(154), snap(171)));
        path2.add(new Vec2(snap(165), snap(220)));
        path2.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(260)));
        path2.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 3, 9 waypoints. Original: dd.
        Path path3 = new Path(9);
        addPath(path3);
        path3.add(new Vec2(snap(GameScreen.WIDTH - 74), snap(-10)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 57), snap(36)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 88), snap(50)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 93), snap(76)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 118), snap(107)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 153), snap(122)));
        path3.add(new Vec2(snap(GameScreen.WIDTH - 171), snap(148)));
        path3.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(250)));
        path3.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 4, 8 waypoints. Original: dd.
        Path path4 = new Path(8);
        addPath(path4);
        path4.add(new Vec2(snap(153), snap(-10)));
        path4.add(new Vec2(snap(139), snap(68)));
        path4.add(new Vec2(snap(166), snap(79)));
        path4.add(new Vec2(snap(161), snap(94)));
        path4.add(new Vec2(snap(158), snap(126)));
        path4.add(new Vec2(snap(172), snap(182)));
        path4.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(220)));
        path4.add(new Vec2(snap(GameScreen.WIDTH / 2), snap(GameScreen.HEIGHT - 58)));
        // 20 enemies every 4200 ms, the first after 30000 ms. Original: ma.
        addWave(new Wave(30000, 20, 4200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(4);
                waiting.add(spawn(paths[4], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 30 enemies every 4200 ms, the first after 20000 ms. Original: mb.
        addWave(new Wave(20000, 30, 4200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                if (remaining % 2 == 1) {
                    interval -= 60;
                }
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER, false));
            }
        });
        // 20 enemies every 2900 ms, the first after 20000 ms. Original: lx.
        addWave(new Wave(20000, 20, 2900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[Randoms.pick(5)], EnemyType.TRIANGLE, false));
            }
        });
        // 20 enemies every 4000 ms, the first after 20000 ms. Original: ly.
        addWave(new Wave(20000, 20, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[Randoms.pick(5)], EnemyType.BALL, false));
            }
        });
        // 10 enemies every 2000 ms, the first after 20000 ms. Original: lv.
        addWave(new Wave(20000, 10, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
            }
        });
        // 20 enemies every 3000 ms, the first after 20000 ms. Original: lw.
        addWave(new Wave(20000, 20, 3000) {
            /** Waypoint index the wave is walking now. Original: lw.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[index], EnemyType.BLOB, false));
                index = (index + 1) % 5;
            }
        });
        // 20 enemies every 5000 ms, the first after 20000 ms. Original: lt.
        addWave(new Wave(20000, 20, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[Randoms.between(0, 2)], EnemyType.SUN_THREE, false));
                waiting.add(spawn(paths[Randoms.between(3, 4)], EnemyType.SUN_THREE, false));
            }
        });
        // 10 enemies every 3000 ms, the first after 20000 ms. Original: lu.
        addWave(new Wave(20000, 10, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.BLOB, false));
                waiting.add(spawn(paths[3], EnemyType.BLOB, false));
            }
        });
        // 20 enemies every 3800 ms, the first after 20000 ms. Original: lr.
        addWave(new Wave(20000, 20, 3800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 4);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 20 enemies every 2600 ms, the first after 20000 ms. Original: rq.
        addWave(new Wave(20000, 20, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.CIRCLE_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.CIRCLE_TWO, false));
            }
        });
        // 12 enemies every 4500 ms, the first after 20000 ms. Original: rp.
        addWave(new Wave(20000, 12, 4500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(4);
                waiting.add(spawn(paths[4], EnemyType.SHREDDER_THREE, false));
            }
        });
        // 50 enemies every 2200 ms, the first after 20000 ms. Original: ro.
        addWave(new Wave(20000, 50, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[Randoms.pick(5)], EnemyType.GHOST, false));
                interval = Randoms.between(900, 2000);
            }
        });
        // 50 enemies every 2000 ms, the first after 20000 ms. Original: rl.
        addWave(new Wave(20000, 50, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                switch (remaining % 5) {
                    case 0: {
                        waiting.add(spawn(paths[4], EnemyType.SHREDDER, false).setHealth(60));
                        return;
                    }
                    case 1: {
                        waiting.add(spawn(paths[3], EnemyType.BALL, false).setHealth(80));
                        return;
                    }
                    case 2: {
                        waiting.add(spawn(paths[2], EnemyType.SUN_THREE, false));
                        return;
                    }
                    case 3: {
                        waiting.add(spawn(paths[1], EnemyType.GHOST_TWO, false));
                        return;
                    }
                    case 4: {
                        waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                    }
                }
            }
        });
        // 30 enemies every 3600 ms, the first after 20000 ms. Original: rz.
        addWave(new Wave(20000, 30, 3600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                if (remaining % 2 == 1) {
                    interval -= 100;
                }
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
                waiting.add(spawn(paths[1], EnemyType.GHOST, false));
                waiting.add(spawn(paths[2], EnemyType.LINE_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.LINE_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.GHOST, false));
            }
        });
        // 26 enemies every 2450 ms, the first after 20000 ms. Original: rx.
        addWave(new Wave(20000, 26, 2450) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining < 8) {
                    interval = 3000;
                    waiting.add(spawn(paths[0], EnemyType.WORM_TWO, true));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.WORM, true));
            }
        });
        // 20 enemies every 2800 ms, the first after 20000 ms. Original: rv.
        addWave(new Wave(20000, 20, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 0) {
                    waiting.add(spawn(paths[0], EnemyType.BALL_TWO, true));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.BALL_TWO, true));
            }
        });
        // 28 enemies every 1800 ms, the first after 20000 ms. Original: rs.
        addWave(new Wave(20000, 28, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.VIRUS_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.VIRUS_TWO, false));
            }
        });
        // 10 enemies every 2600 ms, the first after 20000 ms. Original: rh.
        addWave(new Wave(20000, 10, 2600) {
            /** Waypoint index the wave is walking now. Original: rh.d. */
            int index = Randoms.pick(5);
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[index], EnemyType.CLOCK, false));
            }
        });
        // 20 enemies every 2500 ms, the first after 20000 ms. Original: rf.
        addWave(new Wave(20000, 20, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.SHREDDER, false));
            }
        });
        // 20 enemies every 2600 ms, the first after 20000 ms. Original: rb.
        addWave(new Wave(20000, 20, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[Randoms.pick(5)], EnemyType.SPARK, false));
            }
        });
        // 40 enemies every 2500 ms, the first after 20000 ms. Original: qq.
        addWave(new Wave(20000, 40, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                if (remaining >= 30) {
                    waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                    waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, false));
                    waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
                    waiting.add(spawn(paths[3], EnemyType.SHREDDER, false));
                    waiting.add(spawn(paths[4], EnemyType.SHREDDER_TWO, false));
                    return;
                }
                if (remaining >= 20) {
                    waiting.add(spawn(paths[0], EnemyType.SHREDDER, false).setMoney(0));
                    waiting.add(spawn(paths[1], EnemyType.SHREDDER, false).setMoney(0));
                    waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false).setMoney(0));
                    waiting.add(spawn(paths[3], EnemyType.SHREDDER_TWO, false).setMoney(0));
                    waiting.add(spawn(paths[4], EnemyType.SHREDDER_THREE, false).setMoney(0));
                    return;
                }
                if (remaining >= 10) {
                    waiting.add(spawn(paths[0], EnemyType.SHREDDER_THREE, false).setMoney(0));
                    waiting.add(spawn(paths[1], EnemyType.SHREDDER_THREE, false).setMoney(0));
                    waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false).setMoney(0));
                    waiting.add(spawn(paths[3], EnemyType.SHREDDER_TWO, false).setMoney(0));
                    waiting.add(spawn(paths[4], EnemyType.SHREDDER, false).setMoney(0));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_THREE, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.SHREDDER_THREE, false));
            }
        });
        // 30 enemies every 2600 ms, the first after 20000 ms. Original: qs.
        addWave(new Wave(20000, 30, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[0], EnemyType.VIRUS, false));
                waiting.add(spawn(paths[1], EnemyType.VIRUS, false));
                if (remaining <= 20) {
                    waiting.add(spawn(paths[2], EnemyType.VIRUS_THREE, false));
                }
                if (remaining <= 10) {
                    waiting.add(spawn(paths[3], EnemyType.VIRUS_THREE, false));
                }
                if (remaining == 3) {
                    interval = 6000;
                    waiting.add(spawn(paths[4], EnemyType.VIRUS_BIG, false));
                    return;
                }
                if (remaining == 2) {
                    interval = 6000;
                    waiting.add(spawn(paths[1], EnemyType.VIRUS_BIG, false));
                    return;
                }
                if (remaining == 1) {
                    waiting.add(spawn(paths[0], EnemyType.VIRUS_BIG, false));
                }
            }
        });
        hud.start(200, waves());
    }
}
