/*
 * This file is part of adventure, licensed under the MIT License.
 *
 * Copyright (c) 2017-2021 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.adventure.text;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.Style;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.requireNonNull;

final class StorageNBTComponentImpl extends NBTComponentImpl<StorageNBTComponent, StorageNBTComponent.Builder> implements StorageNBTComponent {
  private final Key storage;

  StorageNBTComponentImpl(final @NotNull List<? extends ComponentLike> children, final @NotNull Style style, final String nbtPath, final boolean interpret, final @Nullable ComponentLike separator, final Key storage) {
    super(children, style, nbtPath, interpret, separator);
    this.storage = storage;
  }

  @Override
  public @NotNull StorageNBTComponent nbtPath(final @NotNull String nbtPath) {
    if (Objects.equals(this.nbtPath, nbtPath)) return this;
    return new StorageNBTComponentImpl(this.children, this.style, requireNonNull(nbtPath, "nbt path"), this.interpret, this.separator, this.storage);
  }

  @Override
  public @NotNull StorageNBTComponent interpret(final boolean interpret) {
    if (this.interpret == interpret) return this;
    return new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.storage);
  }

  @Override
  public @Nullable Component separator() {
    return this.separator;
  }

  @Override
  public @NotNull StorageNBTComponent separator(final @Nullable ComponentLike separator) {
    return new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.storage);
  }

  @Override
  public @NotNull Key storage() {
    return this.storage;
  }

  @Override
  public @NotNull StorageNBTComponent storage(final @NotNull Key storage) {
    if (Objects.equals(this.storage, storage)) return this;
    return new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, requireNonNull(storage, "storage"));
  }

  @Override
  public @NotNull StorageNBTComponent children(final @NotNull List<? extends ComponentLike> children) {
    return new StorageNBTComponentImpl(requireNonNull(children, "children"), this.style, this.nbtPath, this.interpret, this.separator, this.storage);
  }

  @Override
  public @NotNull StorageNBTComponent style(final @NotNull Style style) {
    return new StorageNBTComponentImpl(this.children, requireNonNull(style, "style"), this.nbtPath, this.interpret, this.separator, this.storage);
  }

  @Override
  public boolean equals(final @Nullable Object other) {
    if (this == other) return true;
    if (!(other instanceof StorageNBTComponent)) return false;
    if (!super.equals(other)) return false;
    final StorageNBTComponentImpl that = (StorageNBTComponentImpl) other;
    return Objects.equals(this.storage, that.storage());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = (31 * result) + this.storage.hashCode();
    return result;
  }

  @Override
  protected @NotNull Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
    return Stream.concat(
      Stream.of(
        ExaminableProperty.of("storage", this.storage)
      ),
      super.examinablePropertiesWithoutChildren()
    );
  }

  @Override
  public StorageNBTComponent.@NotNull Builder toBuilder() {
    return new BuilderImpl(this);
  }

  static class BuilderImpl extends NBTComponentImpl.BuilderImpl<StorageNBTComponent, StorageNBTComponent.Builder> implements StorageNBTComponent.Builder {
    private @Nullable Key storage;

    BuilderImpl() {
    }

    BuilderImpl(final @NotNull StorageNBTComponent component) {
      super(component);
      this.storage = component.storage();
    }

    @Override
    public StorageNBTComponent.@NotNull Builder storage(final @NotNull Key storage) {
      this.storage = requireNonNull(storage, "storage");
      return this;
    }

    @Override
    public @NotNull StorageNBTComponent build() {
      if (this.nbtPath == null) throw new IllegalStateException("nbt path must be set");
      if (this.storage == null) throw new IllegalStateException("storage must be set");
      return new StorageNBTComponentImpl(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.storage);
    }
  }
}
