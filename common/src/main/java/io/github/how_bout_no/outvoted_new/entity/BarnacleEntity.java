//package io.github.how_bout_no.outvoted.entity;
//
//import io.github.how_bout_no.outvoted.Outvoted;
//import io.github.how_bout_no.outvoted.init.ModSounds;
//import net.minecraft.entity.*;
//import net.minecraft.entity.ai.control.LookControl;
//import net.minecraft.entity.ai.control.MoveControl;
//import net.minecraft.entity.ai.goal.*;
//import net.minecraft.entity.ai.pathing.EntityNavigation;
//import net.minecraft.entity.ai.pathing.PathNodeType;
//import net.minecraft.entity.ai.pathing.SwimNavigation;
//import net.minecraft.entity.attribute.DefaultAttributeContainer;
//import net.minecraft.entity.attribute.EntityAttributes;
//import net.minecraft.entity.damage.DamageSource;
//import net.minecraft.entity.data.DataTracker;
//import net.minecraft.entity.data.TrackedData;
//import net.minecraft.entity.data.TrackedDataHandlerRegistry;
//import net.minecraft.entity.mob.HostileEntity;
//import net.minecraft.entity.passive.DolphinEntity;
//import net.minecraft.entity.passive.VillagerEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.vehicle.BoatEntity;
//import net.minecraft.inventory.Inventory;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.sound.SoundEvent;
//import net.minecraft.tag.FluidTags;
//import net.minecraft.util.ItemScatterer;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.*;
//import org.jetbrains.annotations.Nullable;
//import software.bernie.geckolib3.core.IAnimatable;
//import software.bernie.geckolib3.core.PlayState;
//import software.bernie.geckolib3.core.builder.AnimationBuilder;
//import software.bernie.geckolib3.core.controller.AnimationController;
//import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
//import software.bernie.geckolib3.core.manager.AnimationData;
//import software.bernie.geckolib3.core.manager.AnimationFactory;
//import software.bernie.geckolib3.resource.GeckoLibCache;
//
//import java.util.*;
//
//public class BarnacleEntity extends HostileEntity implements IAnimatable {
//    private static final TrackedData<Integer> ATTACKING;
//    private static final TrackedData<Integer> TARGET_ENTITY;
//    private LivingEntity targetedEntity;
//    private static final Map<Integer, UUID> targetedEntities = new HashMap<>();
//    private int clientSideAttackTime;
//    private boolean clientSideTouchedGround;
//    protected WanderAroundGoal wander;
//    private boolean initAttack = false;
//    private int attackCounter = 0;
//
//    public BarnacleEntity(EntityType<? extends BarnacleEntity> type, World worldIn) {
//        super(type, worldIn);
//        this.experiencePoints = 10;
//        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
//        this.moveControl = new MoveHelperController(this);
//    }
//
//    protected void initGoals() {
//        GoToWalkTargetGoal movetowardsrestrictiongoal = new GoToWalkTargetGoal(this, 1.0D);
//        this.wander = new WanderAroundGoal(this, 1.0D, 80);
//        this.goalSelector.add(3, new AttackGoal(this));
//        this.goalSelector.add(4, new ChaseGoal(this, 6.0D, 48.0F));
//        this.goalSelector.add(5, new FleeEntityGoal<>(this, BarnacleEntity.class, 72.0F, 4.0D, 4.0D));
//        this.goalSelector.add(6, movetowardsrestrictiongoal);
//        this.goalSelector.add(7, this.wander);
//        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
//        this.goalSelector.add(9, new LookAroundGoal(this));
//        this.wander.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
//        movetowardsrestrictiongoal.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
//        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
//        this.targetSelector.add(2, new ActiveTargetGoal<>(this, DolphinEntity.class, true));
//        this.targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
//    }
//
//    public static DefaultAttributeContainer.Builder setCustomAttributes() {
//        return HostileEntity.createLivingAttributes()
//                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D)
//                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1D)
//                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D);
//    }
//
//    @Nullable
//    public EntityData initialize(ServerWorldAccess worldIn, LocalDifficulty difficultyIn, SpawnReason reason, @Nullable EntityData spawnDataIn, @Nullable NbtCompound dataTag) {
//        HealthUtil.setConfigHealth(this, Outvoted.commonConfig.entities.barnacle.health);
//
//        return super.initialize(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
//    }
//
//    public static boolean canSpawn(EntityType<BarnacleEntity> entity, WorldAccess world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
//        return blockPos.getY() >= world.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ()) && world.getDifficulty() != Difficulty.PEACEFUL && blockPos.getY() <= 45.0 && (spawnReason == SpawnReason.SPAWNER || world.getFluidState(blockPos).isIn(FluidTags.WATER));
//    }
//
//    public int getLimitPerChunk() {
//        return 1;
//    }
//
//    protected EntityNavigation createNavigation(World worldIn) {
//        return new SwimNavigation(this, worldIn);
//    }
//
//    static {
//        ATTACKING = DataTracker.registerData(BarnacleEntity.class, TrackedDataHandlerRegistry.INTEGER);
//        TARGET_ENTITY = DataTracker.registerData(BarnacleEntity.class, TrackedDataHandlerRegistry.INTEGER);
//    }
//
//    @Override
//    protected void initDataTracker() {
//        super.initDataTracker();
//        this.dataTracker.startTracking(TARGET_ENTITY, 0);
//        this.dataTracker.startTracking(ATTACKING, 0);
//    }
//
//    public int getAttackDuration() {
//        return 80;
//    }
//
//    private void setTargetedEntity(int entityId) {
//        this.dataTracker.set(TARGET_ENTITY, entityId);
//    }
//
//    public boolean hasTargetedEntity() {
//        return this.dataTracker.get(TARGET_ENTITY) != 0;
//    }
//
//    private void setAttacking(int attacking) {
//        this.dataTracker.set(ATTACKING, attacking);
//    }
//
//    public int getAttackPhase() {
//        return this.dataTracker.get(ATTACKING);
//    }
//
//    @Nullable
//    public LivingEntity getTargetedEntity() {
//        if (!this.hasTargetedEntity()) {
//            return null;
//        } else if (this.world.isClient) {
//            if (this.targetedEntity != null) {
//                return this.targetedEntity;
//            } else {
//                Entity entity = this.world.getEntityById(this.dataTracker.get(TARGET_ENTITY));
//                if (entity instanceof LivingEntity) {
//                    this.targetedEntity = (LivingEntity) entity;
//                    return this.targetedEntity;
//                } else {
//                    return null;
//                }
//            }
//        } else {
//            return this.getTarget();
//        }
//    }
//
//    @Override
//    public boolean canBreatheInWater() {
//        return true;
//    }
//
//    @Override
//    public EntityGroup getGroup() {
//        return EntityGroup.AQUATIC;
//    }
//
//    protected void updateAir(int air) {
//        if (this.isAlive() && !this.isInsideWaterOrBubbleColumn()) {
//            this.setAir(air - 1);
//            if (this.getAir() == -20) {
//                this.setAir(0);
//                this.damage(DamageSource.DROWN, 5.0F);
//            }
//        } else {
//            this.setAir(300);
//        }
//
//    }
//
//    public void onTrackedDataSet(TrackedData<?> key) {
//        super.onTrackedDataSet(key);
//        if (TARGET_ENTITY.equals(key)) {
//            this.clientSideAttackTime = 0;
//            this.targetedEntity = null;
//        }
//
//    }
//
//    /**
//     * Get number of ticks, at least during which the living entity will be silent.
//     */
//    public int getMinAmbientSoundDelay() {
//        return 160;
//    }
//
//    protected SoundEvent getAmbientSound() {
//        return ModSounds.BARNACLE_AMBIENT.get();
//    }
//
//    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
//        return ModSounds.BARNACLE_HURT.get();
//    }
//
//    protected SoundEvent getDeathSound() {
//        return ModSounds.BARNACLE_DEATH.get();
//    }
//
//    protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
//        return sizeIn.height * 0.4F;
//    }
//
//    public float getPathfindingFavor(BlockPos pos, WorldView worldIn) {
//        return worldIn.getFluidState(pos).isIn(FluidTags.WATER) ? 10.0F + worldIn.getBrightness(pos) - 0.5F : super.getPathfindingFavor(pos, worldIn);
//    }
//
//    @Override
//    public void onDeath(DamageSource cause) {
//        targetedEntities.remove(this.dataTracker.get(TARGET_ENTITY));
//        super.onDeath(cause);
//    }
//
//    @Override
//    public void takeKnockback(double strength, double x, double z) {
//        super.takeKnockback(strength / 4, x, z);
//    }
//
//    /**
//     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
//     * use this to react to sunlight and start to burn.
//     */
//    public void tickMovement() {
//        super.tickMovement();
//        if (this.isAlive()) {
//            if (this.world.isClient) {
//                if (!this.isTouchingWater()) {
//                    Vec3d vector3d = this.getVelocity();
//                    if (vector3d.y > 0.0D && this.clientSideTouchedGround && !this.isSilent()) {
//                        this.world.playSound(this.getX(), this.getY(), this.getZ(), this.getFlopSound(), this.getSoundCategory(), 1.0F, 1.0F, false);
//                    }
//
//                    this.clientSideTouchedGround = vector3d.y < 0.0D && this.world.isTopSolid(this.getBlockPos().down(), this);
//                }
//                if (this.hasTargetedEntity()) {
//                    if (this.clientSideAttackTime < this.getAttackDuration()) {
//                        ++this.clientSideAttackTime;
//                    }
//                }
//            }
//            if (this.hasTargetedEntity()) {
//                this.setYaw(this.headYaw);
//                LivingEntity livingentity = this.getTargetedEntity();
//                if (livingentity != null) {
//                    this.getLookControl().lookAt(livingentity, 90.0F, 90.0F);
//                    this.getLookControl().tick();
//                    double d5 = this.getAttackAnimationScale(0.0F);
//                    double d0 = livingentity.getX() - this.getX();
//                    double d1 = livingentity.getBodyY(0.5D) - this.getEyeY();
//                    double d2 = livingentity.getZ() - this.getZ();
//                    double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
//                    d0 = d0 / d3;
//                    d1 = d1 / d3;
//                    d2 = d2 / d3;
//                    double d4 = this.random.nextDouble();
//                    if (this.getAttackPhase() == 0) {
//                        this.setAttacking(1);
//                    }
//
//                    while (d4 < d3) {
//                        d4 += 1.8D - d5 + this.random.nextDouble() * (1.7D - d5);
//                        livingentity.refreshPositionAndAngles(this.getX() + d0 * d3, this.getEyeY() + d1, this.getZ() + d2 * d3, livingentity.getYaw(), livingentity.getPitch());
//                        livingentity.setSwimming(false);
//                        livingentity.updateSwimming();
//                        if (!this.world.isClient) {
//                            if (livingentity.hasVehicle() && livingentity.getRootVehicle() instanceof BoatEntity) {
//                                Entity boat = livingentity.getRootVehicle();
//                                livingentity.stopRiding();
//                                boat.dropItem(((BoatEntity) boat).asItem());
//                                try {
//                                    ItemScatterer.spawn(boat.world, boat, (Inventory) boat);
//                                } catch (Exception ignored) {
//                                }
//                                boat.discard();
//                            }
//                        }
//                        if (this.getAttackPhase() != 0) {
//                            if (attackCounter > 10) {
//                                if (!initAttack) {
//                                    livingentity.damage(DamageSource.mob(this), 0.1F);
//                                    initAttack = true;
//                                }
//                            } else {
//                                attackCounter++;
//                            }
//                            livingentity.addVelocity(-d0 / 50, -d1 / 50, -d2 / 50);
//                        } else if (initAttack) {
//                            initAttack = false;
//                        } else {
//                            attackCounter = 0;
//                        }
//                    }
//                }
//            }
//
//            if (this.isInsideWaterOrBubbleColumn()) {
//                this.setAir(300);
//            } else if (this.onGround) {
//                this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.1F, 0.5D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.1F));
//                this.setYaw(this.random.nextFloat() * 360.0F);
//                this.onGround = false;
//                this.velocityDirty = true;
//            }
//        }
//    }
//
//    public void baseTick() {
//        int i = this.getAir();
//        super.baseTick();
//        this.updateAir(i);
//    }
//
//    protected SoundEvent getFlopSound() {
//        return ModSounds.BARNACLE_FLOP.get();
//    }
//
//    public float getAttackAnimationScale(float f) {
//        return ((float) this.clientSideAttackTime + f) / (float) this.getAttackDuration();
//    }
//
//    public boolean canSpawn(WorldView worldIn) {
//        return worldIn.intersectsEntities(this);
//    }
//
//    /**
//     * Called when the entity is attacked.
//     */
//    public boolean damage(DamageSource source, float amount) {
//        if (this.wander != null) {
//            this.wander.ignoreChanceOnce();
//        }
//
//        return super.damage(source, amount);
//    }
//
//    public void travel(Vec3d travelVector) {
//        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
//            this.updateVelocity(0.1F, travelVector);
//            this.move(MovementType.SELF, this.getVelocity());
//            this.setVelocity(this.getVelocity().multiply(0.9D));
//            if (this.getMovementSpeed() == 0.0F && this.getTarget() == null) {
//                this.setVelocity(this.getVelocity().add(0.0D, -0.001D, 0.0D));
//            } else if (this.getTarget() != null) {
//                this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
//            }
//        } else {
//            super.travel(travelVector);
//        }
//
//    }
//
//    protected void mobTick() {
//        super.mobTick();
//        if (!this.hasPositionTarget()) {
//            this.setPositionTarget(this.getBlockPos(), 96);
//        }
//    }
//
//    public boolean waterCheck(LivingEntity livingentity) {
//        if (livingentity.getVehicle() != null) {
//            return livingentity.getVehicle().isTouchingWater();
//        } else {
//            return livingentity.isTouchingWater() && (targetedEntities.get(livingentity.getId()) == null || targetedEntities.get(livingentity.getId()) == this.getUuid());
//        }
//    }
//
//    static class ChaseGoal extends WanderNearTargetGoal {
//        private final BarnacleEntity mob;
//        private final double speed;
//
//        public ChaseGoal(BarnacleEntity barnacle, double speedIn, float maxDistanceIn) {
//            super(barnacle, speedIn, maxDistanceIn);
//            this.mob = barnacle;
//            this.speed = speedIn;
//        }
//
//        public void stop() {
//        }
//
//        public boolean canStart() {
//            LivingEntity livingentity = this.mob.getTarget();
//            if (livingentity != null) {
//                return super.canStart() && this.mob.waterCheck(livingentity) && this.mob.isInWalkTargetRange();
//            } else {
//                return false;
//            }
//        }
//
//        public boolean shouldContinue() {
//            LivingEntity livingentity = this.mob.getTarget();
//            if (livingentity != null) {
//                return super.shouldContinue() && this.mob.waterCheck(livingentity) && this.mob.isInWalkTargetRange();
//            } else {
//                return false;
//            }
//        }
//
//        public void start() {
//        }
//
//        public void tick() {
//            LivingEntity livingentity = this.mob.getTarget();
//            if (livingentity != null) {
//                this.mob.getNavigation().startMovingTo(livingentity, this.speed);
//            }
//        }
//    }
//
//    static class AttackGoal extends Goal {
//        private final BarnacleEntity mob;
//        private int tickCounter;
//        private boolean hasAttacked;
//
//        public AttackGoal(BarnacleEntity entity) {
//            this.mob = entity;
//            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
//        }
//
//        public boolean canStart() {
//            LivingEntity livingentity = this.mob.getTarget();
//            return livingentity != null && livingentity.isAlive() && this.mob.waterCheck(livingentity) && this.mob.squaredDistanceTo(this.mob.getTarget()) < 64.0D;
//        }
//
//        public boolean shouldContinue() {
//            if (this.mob.getTarget() != null) {
//                return this.mob.squaredDistanceTo(this.mob.getTarget()) < 90.5D && this.mob.waterCheck(this.mob.getTarget());
//            } else {
//                return false;
//            }
//        }
//
//        public void start() {
//            this.tickCounter = -1;
//            this.mob.getNavigation().stop();
//            this.mob.getLookControl().lookAt(this.mob.getTarget(), 90.0F, 90.0F);
//            this.mob.velocityDirty = true;
//            this.hasAttacked = false;
//        }
//
//        public void stop() {
//            targetedEntities.remove(this.mob.dataTracker.get(TARGET_ENTITY));
//            this.mob.setTargetedEntity(0);
//            this.mob.setTarget(null);
//            this.mob.wander.ignoreChanceOnce();
//            this.mob.setAttacking(0);
//            this.hasAttacked = false;
//        }
//
//        public void tick() {
//            LivingEntity livingentity = this.mob.getTarget();
//            if (livingentity != null) {
//                this.mob.getNavigation().stop();
//                this.mob.getLookControl().lookAt(livingentity, 90.0F, 90.0F);
//                if (!this.mob.canSee(livingentity)) {
//                    this.mob.setTarget(null);
//                } else {
//                    ++this.tickCounter;
//                    if (this.mob.getAttackPhase() < 1) this.mob.setAttacking(1);
//                    if (this.tickCounter == 0) {
//                        this.mob.setTargetedEntity(this.mob.getTarget().getId());
//                        targetedEntities.put(livingentity.getId(), this.mob.getUuid());
//                    } else if (this.tickCounter >= this.mob.getAttackDuration()) {
//                        if (this.tickCounter >= 600) {
//                            this.mob.setAttacking(2);
//                            if (this.tickCounter % 5 == 0) livingentity.damage(DamageSource.mob(this.mob), 2.0F);
//                        } else if (this.tickCounter % 40 == 0 && this.mob.getAttackPhase() == 1) {
//                            this.mob.setAttacking(3);
//                        } else if (this.mob.getAttackPhase() == 3 && this.tickCounter % 6 == 0) {
//                            if (!this.hasAttacked) {
//                                livingentity.damage(DamageSource.mob(this.mob), 2.0F);
//                            } else {
//                                this.mob.setAttacking(1);
//                            }
//                            this.hasAttacked = !this.hasAttacked;
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    static class MoveHelperController extends MoveControl {
//        private final BarnacleEntity mob;
//
//        public MoveHelperController(BarnacleEntity entity) {
//            super(entity);
//            this.mob = entity;
//        }
//
//        public void tick() {
//            if (this.state == State.MOVE_TO && !this.mob.getNavigation().isIdle()) {
//                Vec3d vector3d = new Vec3d(this.targetX - this.mob.getX(), this.targetY - this.mob.getY(), this.targetZ - this.mob.getZ());
//                double d0 = vector3d.length();
//                double d1 = vector3d.x / d0;
//                double d2 = vector3d.y / d0;
//                double d3 = vector3d.z / d0;
//                float f = (float) (MathHelper.atan2(vector3d.z, vector3d.x) * (double) (180F / (float) Math.PI)) - 90.0F;
//                this.mob.setYaw(this.wrapDegrees(this.mob.getYaw(), f, 90.0F));
//                this.mob.bodyYaw = this.mob.getYaw();
//                float f1 = (float) (this.speed * this.mob.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
//                float f2 = MathHelper.lerp(0.125F, this.mob.getMovementSpeed(), f1);
//                this.mob.setMovementSpeed(f2);
//                double d4 = Math.sin((double) (this.mob.age + this.mob.getId()) * 0.5D) * 0.05D;
//                double d5 = Math.cos(this.mob.getYaw() * ((float) Math.PI / 180F));
//                double d6 = Math.sin(this.mob.getYaw() * ((float) Math.PI / 180F));
//                double d7 = Math.sin((double) (this.mob.age + this.mob.getId()) * 0.75D) * 0.05D;
//                this.mob.setVelocity(this.mob.getVelocity().add(d4 * d5, d7 * (d6 + d5) * 0.25D + (double) f2 * d2 * 0.1D, d4 * d6));
//                LookControl lookcontroller = this.mob.getLookControl();
//                double d8 = this.mob.getX() + d1 * 2.0D;
//                double d9 = this.mob.getEyeY() + d2 / d0;
//                double d10 = this.mob.getZ() + d3 * 2.0D;
//                double d11 = lookcontroller.getLookX();
//                double d12 = lookcontroller.getLookY();
//                double d13 = lookcontroller.getLookZ();
//                if (!lookcontroller.isActive()) {
//                    d11 = d8;
//                    d12 = d9;
//                    d13 = d10;
//                }
//
//                this.mob.getLookControl().lookAt(MathHelper.lerp(0.125D, d11, d8), MathHelper.lerp(0.125D, d12, d9), MathHelper.lerp(0.125D, d13, d10), 10.0F, 40.0F);
//            } else {
//                this.mob.setMovementSpeed(0.0F);
//            }
//        }
//    }
//
//    private final AnimationFactory factory = new AnimationFactory(this);
//
//    public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//        int phase = this.getAttackPhase();
//        LivingEntity livingEntity = this.getTargetedEntity();
//        if (this.hasTargetedEntity() && phase > 0 && livingEntity != null && livingEntity.getPos() != null) {
//            GeckoLibCache.getInstance().parser.setValue("distance", this.squaredDistanceTo(livingEntity) + 15);
//        }
//        if (event.getController().getCurrentAnimation() == null || event.getController().getCurrentAnimation().animationName == null) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("swim"));
//            return PlayState.CONTINUE;
//        }
//        switch (phase) {
//            case 1 -> {
//                if (event.getController().getCurrentAnimation().animationName.equals("bite")) {
//                    event.getController().setAnimation(new AnimationBuilder().addAnimation("reelin"));
//                } else {
//                    event.getController().setAnimation(new AnimationBuilder().addAnimation("attack").addAnimation("reelin"));
//                }
//                return PlayState.CONTINUE;
//            }
//            case 2 -> {
//                event.getController().setAnimation(new AnimationBuilder().addAnimation("clamp"));
//                return PlayState.CONTINUE;
//            }
//            case 3 -> {
//                event.getController().setAnimation(new AnimationBuilder().addAnimation("bite"));
//                return PlayState.CONTINUE;
//            }
//            default -> {
//                event.getController().setAnimation(new AnimationBuilder().addAnimation("swim"));
//                return PlayState.CONTINUE;
//            }
//        }
//    }
//
//    @Override
//    public void registerControllers(AnimationData data) {
//        AnimationController<BarnacleEntity> controller = new AnimationController<>(this, "controller", 3, this::predicate);
//        data.addAnimationController(controller);
//    }
//
//    @Override
//    public AnimationFactory getFactory() {
//        return this.factory;
//    }
//}