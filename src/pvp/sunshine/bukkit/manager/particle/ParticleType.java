package pvp.sunshine.bukkit.manager.particle;

import pvp.sunshine.bukkit.BukkitMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class ParticleType {

	private Map<Player, Integer> animatedHelixID = new HashMap<Player, Integer>();
	private Map<Player, Integer> intID = new HashMap<Player, Integer>();
	private Map<Player, Integer> intIDspheric = new HashMap<Player, Integer>();
	private Map<Player, Integer> radar = new HashMap<Player, Integer>();
	private Map<Player, Integer> Helix = new HashMap<Player, Integer>();
	private Map<Player, Integer> otherroration = new HashMap<Player, Integer>();
	private Map<Player, Integer> circleofparticle = new HashMap<Player, Integer>();

	public void spiraleEffect(Player p, ParticleEffect type) {
		if (!this.intID.containsKey(p)) {
			int sp = Bukkit.getServer().getScheduler().runTaskTimer(BukkitMain.getInstance(), new Runnable() {
				float i = 0.0F;

				public void run() {
					for (int k = 0; k < 1; k++) {
						Location l = p.getLocation();
						double x = Math.sin(this.i * 3.7F);
						double y = Math.cos(this.i * 3.7F);
						double z = this.i * 0.4F;
						Vector v = new Vector(x, z, y);
						l.add(v);
						type.display(0.0F, 0.0F, 0.0F, 3.0F, 1, l, 25.0D);
					}
					this.i += 0.1F;
					if (this.i > 5.0F) {
						this.i = 0.0F;
					}
				}
			}, 1L, 1L).getTaskId();
			this.intID.put(p, Integer.valueOf(sp));
		}
	}

	public boolean hasEffect(Player p) {
		if (this.intID.containsKey(p)) {
			return true;
		}
		if (this.intIDspheric.containsKey(p)) {
			return true;
		}
		if (this.radar.containsKey(p)) {
			return true;
		}
		if (this.Helix.containsKey(p)) {
			return true;
		}
		if (this.animatedHelixID.containsKey(p)) {
			return true;
		}
		if (this.otherroration.containsKey(p)) {
			return true;
		}
		if (this.circleofparticle.containsKey(p)) {
			return true;
		}
		return false;
	}

	public void rorationEffect(Player p, ParticleEffect type) {
		if (!this.intIDspheric.containsKey(p)) {
			int rt = Bukkit.getServer().getScheduler().runTaskTimer(BukkitMain.getInstance(), new Runnable() {
				float j = 0.0F;

				public void run() {
					Location loc = p.getLocation();
					loc.setY(loc.getY() + 1.9D + 0.03D);
					for (int k = 0; k < 1.0F; k++) {
						loc.add(Math.cos(this.j) * 0.6000000238418579D, this.j * 0.01F,
								Math.sin(this.j) * 0.6000000238418579D);
						type.display(0.0F, 0.0F, 0.0F, 3.0F, 1, loc, 25.0D);
					}
					this.j += 0.2F;
					if (this.j > 50.0F) {
						this.j = 0.0F;
					}
				}
			}, 1L, 1L).getTaskId();
			this.intIDspheric.put(p, Integer.valueOf(rt));
		}
	}

	public void radarEffect(Player p, ParticleEffect type) {
		if (!this.radar.containsKey(p)) {
			int superS = Bukkit.getServer().getScheduler().runTaskTimer(BukkitMain.getInstance(), new Runnable() {
				float j = 0.0F;

				public void run() {
					Location loc = p.getLocation();
					loc.setY(loc.getY() + 2.0D);
					for (int k = 0; k < 5.0F; k++) {
						loc.setX(loc.getX() + Math.sin(this.j * 0.17453292519943295D) * 0.20000000298023224D);
						loc.setY(loc.getY());

						loc.setZ(loc.getZ() + Math.cos(this.j * 0.17453292519943295D) * 0.20000000298023224D);

						type.display(0.0F, 0.0F, 0.0F, 3.0F, 1, loc, 25.0D);

						this.j += 0.3F;
					}
					if (this.j >= 360.0F) {
						this.j = 0.0F;
					}
				}
			}, 1L, 1L).getTaskId();
			this.radar.put(p, Integer.valueOf(superS));
		}
	}

	public void SpiralEffect(Player p, ParticleEffect particle) {
		if (!this.Helix.containsKey(p)) {
			int helix = Bukkit.getServer().getScheduler().runTaskTimer(BukkitMain.getInstance(), new Runnable() {
				double xRotation;
				double yRotation;
				double zRotation = 5.0D;
				double angularVelocityX = 0.015707963267948967D;
				double angularVelocityY = 0.018479956785822312D;
				double angularVelocityZ = 0.02026833970057931D;
				float radius = 1.5F;
				float step = 0.0F;
				double xSubtract;
				double ySubtract;
				double zSubtract;
				boolean enableRotation = true;
				int particles = 20;

				public void run() {
					Location location = p.getLocation();
					location.add(0.0D, 1.0D, 0.0D);
					location.subtract(this.xSubtract, this.ySubtract, this.zSubtract);
					double inc = 6.283185307179586D / this.particles;
					double angle = this.step * inc;
					Vector v = new Vector();
					v.setX(Math.cos(angle) * this.radius);
					v.setZ(Math.sin(angle) * this.radius);
					rotateVector(v, this.xRotation, this.yRotation, this.zRotation);
					if (this.enableRotation) {
						rotateVector(v, this.angularVelocityX * this.step, this.angularVelocityY * this.step,
								this.angularVelocityZ * this.step);
					}
					particle.display(0.0F, 0.0F, 0.0F, 3.0F, 1, location.add(v), 50.0D);
					this.step += 1.0F;
				}
			}, 1L, 1L).getTaskId();
			this.Helix.put(p, Integer.valueOf(helix));
		}
	}

	public void startHelix(Player p, ParticleList.ParticleType type) {
		if (!this.animatedHelixID.containsKey(p)) {
			int continue1 = Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), new Runnable() {
				double phi = 0.0D;

				public void run() {
					this.phi += 0.39269908169872414D;
					if (!MovimentPlayer.isNotMoving(p)) {
						new ParticleList(type, 0.10000000149011612D, 4, 0.30000001192092896D)
								.sendToLocation(p.getLocation().add(0.0D, 1.0D, 0.0D));
					} else {
						Location l = p.getLocation();
						for (double t = 0.0D; t <= 6.283185307179586D; t += 0.19634954084936207D) {
							for (double i = 0.0D; i <= 1.0D; i += 1.0D) {
								double x = 0.4D * (6.283185307179586D - t) * 0.5D
										* Math.cos(t + this.phi + i * 3.141592653589793D);
								double y = 0.5D * t;
								double z = 0.4D * (6.283185307179586D - t) * 0.5D
										* Math.sin(t + this.phi + i * 3.141592653589793D);
								l.add(x, y, z);
								new ParticleList(type, 0.0D, 1, 1.0E-4D).sendToLocation(l);
								l.subtract(x, y, z);
							}
						}
					}
				}
			}, 0L, 5L).getTaskId();
			this.animatedHelixID.put(p, Integer.valueOf(continue1));
		}
	}

	public void rorationOtherType(Player p, ParticleList.ParticleType ptype) {
		if (!this.intIDspheric.containsKey(p)) {
			int rt = Bukkit.getServer().getScheduler().runTaskTimer(BukkitMain.getInstance(), new Runnable() {
				float j = 0.0F;

				public void run() {
					Location loc = p.getLocation();
					loc.setY(loc.getY() + 1.9D + 0.03D);
					for (int k = 0; k < 1.0F; k++) {
						loc.add(Math.cos(this.j) * 0.6000000238418579D, this.j * 0.01F,
								Math.sin(this.j) * 0.6000000238418579D);

						new ParticleList(ptype, 0.0D, 1, 1.0E-4D).sendToLocation(loc);
					}
					this.j += 0.2F;
					if (this.j > 50.0F) {
						this.j = 0.0F;
					}
				}
			}, 1L, 1L).getTaskId();
			this.otherroration.put(p, Integer.valueOf(rt));
		}
	}

	public void circleOfParticles(Player p, ParticleEffect pe) {
		if (!this.circleofparticle.containsKey(p)) {
			int c = Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), new Runnable() {
				double cosI = 0.0D;

				public void run() {
					if (!MovimentPlayer.isNotMoving(p)) {
						new ParticleList(ParticleList.ParticleType.DRIP_WATER, 0.10000000149011612D, 21,
								0.30000001192092896D).sendToLocation(p.getLocation().add(0.0D, 1.0D, 0.0D));
						new ParticleList(ParticleList.ParticleType.WATER_SPLASH, 0.10000000149011612D, 21,
								0.30000001192092896D).sendToLocation(p.getLocation().add(0.0D, 1.0D, 0.0D));
					} else {
						Location l = p.getLocation();
						this.cosI += 0.3141592653589793D;
						for (double t = 0.0D; t <= 6.283185307179586D; t += 0.07853981633974483D) {
							double x = 1.5D * Math.cos(t) * Math.sin(this.cosI);
							double y = 1.5D * Math.cos(this.cosI) + 1.5D;
							double z = 1.5D * Math.sin(t) * Math.sin(this.cosI);
							l.add(x, y, z);
							pe.display(0.0F, 0.0F, 0.0F, 0.0F, 1, l, 25.0D);
							l.subtract(x, y, z);
						}
					}
				}
			}, 0L, 6L).getTaskId();
			this.circleofparticle.put(p, Integer.valueOf(c));
		}
	}

	public static Vector getBumpVector(Entity paramEntity, Location paramLocation, double paramDouble) {
		Vector localVector = paramEntity.getLocation().toVector().subtract(paramLocation.toVector()).normalize();
		localVector.multiply(paramDouble);
		return localVector;
	}

	public static Vector getPullVector(Entity paramEntity, Location paramLocation, double paramDouble) {
		Vector localVector = paramLocation.toVector().subtract(paramEntity.getLocation().toVector()).normalize();
		localVector.multiply(paramDouble);
		return localVector;
	}

	public static void bumpEntity(Entity paramEntity, Location paramLocation, double paramDouble) {
		paramEntity.setVelocity(getBumpVector(paramEntity, paramLocation, paramDouble));
	}

	public static void bumpEntity(Entity paramEntity, Location paramLocation, double paramDouble1,
			double paramDouble2) {
		Vector localVector = getBumpVector(paramEntity, paramLocation, paramDouble1);
		localVector.setY(paramDouble2);
		paramEntity.setVelocity(localVector);
	}

	public static void pullEntity(Entity paramEntity, Location paramLocation, double paramDouble) {
		paramEntity.setVelocity(getPullVector(paramEntity, paramLocation, paramDouble));
	}

	public static void pullEntity(Entity paramEntity, Location paramLocation, double paramDouble1,
			double paramDouble2) {
		Vector localVector = getPullVector(paramEntity, paramLocation, paramDouble1);
		localVector.setY(paramDouble2);
		paramEntity.setVelocity(localVector);
	}

	public static void velocity(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3) {
		velocity(paramEntity, paramEntity.getLocation().getDirection(), paramDouble1, false, 0.0D, paramDouble2,
				paramDouble3);
	}

	public static void velocity(Entity paramEntity, Vector paramVector, double paramDouble1, boolean paramBoolean,
			double paramDouble2, double paramDouble3, double paramDouble4) {
		if ((Double.isNaN(paramVector.getX())) || (Double.isNaN(paramVector.getY()))
				|| (Double.isNaN(paramVector.getZ())) || (paramVector.length() == 0.0D)) {
			return;
		}
		if (paramBoolean) {
			paramVector.setY(paramDouble2);
		}
		paramVector.normalize();
		paramVector.multiply(paramDouble1);

		paramVector.setY(paramVector.getY() + paramDouble3);
		if (paramVector.getY() > paramDouble4) {
			paramVector.setY(paramDouble4);
		}
		paramEntity.setFallDistance(0.0F);
		paramEntity.setVelocity(paramVector);
	}

	public static final Vector rotateAroundAxisX(Vector paramVector, double paramDouble) {
		double d3 = Math.cos(paramDouble);
		double d4 = Math.sin(paramDouble);
		double d1 = paramVector.getY() * d3 - paramVector.getZ() * d4;
		double d2 = paramVector.getY() * d4 + paramVector.getZ() * d3;
		return paramVector.setY(d1).setZ(d2);
	}

	public static final Vector rotateAroundAxisY(Vector paramVector, double paramDouble) {
		double d3 = Math.cos(paramDouble);
		double d4 = Math.sin(paramDouble);
		double d1 = paramVector.getX() * d3 + paramVector.getZ() * d4;
		double d2 = paramVector.getX() * -d4 + paramVector.getZ() * d3;
		return paramVector.setX(d1).setZ(d2);
	}

	public static final Vector rotateAroundAxisZ(Vector paramVector, double paramDouble) {
		double d3 = Math.cos(paramDouble);
		double d4 = Math.sin(paramDouble);
		double d1 = paramVector.getX() * d3 - paramVector.getY() * d4;
		double d2 = paramVector.getX() * d4 + paramVector.getY() * d3;
		return paramVector.setX(d1).setY(d2);
	}

	public static final Vector rotateVector(Vector paramVector, double paramDouble1, double paramDouble2,
			double paramDouble3) {
		rotateAroundAxisX(paramVector, paramDouble1);
		rotateAroundAxisY(paramVector, paramDouble2);
		rotateAroundAxisZ(paramVector, paramDouble3);
		return paramVector;
	}

	public static final double angleToXAxis(Vector paramVector) {
		return Math.atan2(paramVector.getX(), paramVector.getY());
	}

	public static void velocity(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3,
			boolean paramBoolean) {
		velocity(paramEntity, paramEntity.getLocation().getDirection(), paramDouble1, false, 0.0D, paramDouble2,
				paramDouble3, paramBoolean);
	}

	public static void velocity(Entity paramEntity, Vector paramVector, double paramDouble1, boolean paramBoolean1,
			double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean2) {
		if ((Double.isNaN(paramVector.getX())) || (Double.isNaN(paramVector.getY()))
				|| (Double.isNaN(paramVector.getZ())) || (paramVector.length() == 0.0D)) {
			return;
		}
		if (paramBoolean1) {
			paramVector.setY(paramDouble2);
		}
		paramVector.normalize();
		paramVector.multiply(paramDouble1);

		paramVector.setY(paramVector.getY() + paramDouble3);
		if (paramVector.getY() > paramDouble4) {
			paramVector.setY(paramDouble4);
		}
		if (paramBoolean2) {
			paramVector.setY(paramVector.getY() + 0.2D);
		}
		paramEntity.setFallDistance(0.0F);
		paramEntity.setVelocity(paramVector);
	}

	public void stopAll(Player p) {
		if (this.intID.containsKey(p)) {
			Bukkit.getServer().getScheduler().cancelTask(((Integer) this.intID.get(p)).intValue());
			this.intID.remove(p);
		}
		if (this.intIDspheric.containsKey(p)) {
			Bukkit.getServer().getScheduler().cancelTask(((Integer) this.intIDspheric.get(p)).intValue());
			this.intIDspheric.remove(p);
		}
		if (this.radar.containsKey(p)) {
			Bukkit.getServer().getScheduler().cancelTask(((Integer) this.radar.get(p)).intValue());
			this.radar.remove(p);
		}
		if (this.Helix.containsKey(p)) {
			Bukkit.getServer().getScheduler().cancelTask(((Integer) this.Helix.get(p)).intValue());
			this.Helix.remove(p);
		}
		if (this.animatedHelixID.containsKey(p)) {
			Bukkit.getServer().getScheduler().cancelTask(((Integer) this.animatedHelixID.get(p)).intValue());
			this.animatedHelixID.remove(p);
		}
		if (this.otherroration.containsKey(p)) {
			Bukkit.getServer().getScheduler().cancelTask(((Integer) this.otherroration.get(p)).intValue());
			this.otherroration.remove(p);
		}
		if (this.circleofparticle.containsKey(p)) {
			Bukkit.getServer().getScheduler().cancelTask(((Integer) this.circleofparticle.get(p)).intValue());
			this.circleofparticle.remove(p);
		}
	}

}