package io.jhdf.stream;

import io.jhdf.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class InMemoryFileChannelAdaptor extends FileChannel {

	private final ByteBuffer byteBuffer;

	public InMemoryFileChannelAdaptor(InputStream inputStream) throws IOException {
		this.byteBuffer = ByteBuffer.allocate(inputStream.available());
		Channels.newChannel(inputStream).read(byteBuffer);
		this.byteBuffer.asReadOnlyBuffer();
		this.byteBuffer.position(0);
	}

	public InMemoryFileChannelAdaptor(ByteBuffer byteBuffer) {
		this.byteBuffer = byteBuffer.asReadOnlyBuffer();
		this.byteBuffer.position(0);
	}

	public InMemoryFileChannelAdaptor(byte[] bytes) {
		this.byteBuffer = ByteBuffer.wrap(bytes).asReadOnlyBuffer();
		this.byteBuffer.position(0);
	}

	@Override
	public int read(ByteBuffer dst) throws IOException {
		final int remaining = dst.remaining();
		final byte[] bytes = new byte[remaining];
		byteBuffer.get(bytes);
		dst.put(bytes);
		return remaining;
	}

	@Override
	public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
		long bytesRead = 0;
		for (int i = offset; i < length; i++) {
			bytesRead += read(dsts[i]);
		}
		return bytesRead;
	}

	@Override
	public int write(ByteBuffer src) throws IOException {
		throw new NonWritableChannelException();
	}

	@Override
	public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
		throw new NonWritableChannelException();
	}

	@Override
	public long position() throws IOException {
		return byteBuffer.position();
	}

	@Override
	public FileChannel position(long newPosition) throws IOException {
		byteBuffer.position(Math.toIntExact(newPosition));
		return this;
	}

	@Override
	public long size() throws IOException {
		return byteBuffer.capacity();
	}

	@Override
	public FileChannel truncate(long size) throws IOException {
		throw new NonWritableChannelException();
	}

	@Override
	public void force(boolean metaData) throws IOException {
		throw new NonWritableChannelException();
	}

	@Override
	public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
		ByteBuffer tempBuffer = byteBuffer.slice();
		tempBuffer.position(Math.toIntExact(position));
		tempBuffer.limit(Math.toIntExact(position + count));
		return target.write(tempBuffer);
	}

	@Override
	public long transferFrom(ReadableByteChannel src, long position, long count) throws IOException {
		throw new NonWritableChannelException();
	}

	@Override
	public int read(ByteBuffer dst, long position) throws IOException {
		if(position > byteBuffer.capacity()) {
			return 0;
		}
		ByteBuffer tempBuffer = byteBuffer.slice();
		tempBuffer.position(Math.toIntExact(position));
		final int remaining = dst.remaining();
		final byte[] bytes = new byte[remaining];
		tempBuffer.get(bytes);
		dst.put(bytes);
		return remaining;
	}

	@Override
	public int write(ByteBuffer src, long position) throws IOException {
		throw new NonWritableChannelException();
	}

	@Override
	public MappedByteBuffer map(MapMode mode, long position, long size) throws IOException {
		if(mode != MapMode.READ_ONLY) {
			throw new NonWritableChannelException();
		}

		return new MappedByteBufferAdaptor();
	}

	public class MappedByteBufferAdaptor extends MappedByteBuffer {

		@Override
		public ByteBuffer slice() {
			return null;
		}

		@Override
		public ByteBuffer duplicate() {
			return null;
		}

		@Override
		public ByteBuffer asReadOnlyBuffer() {
			return null;
		}

		@Override
		public byte get() {
			return 0;
		}

		@Override
		public ByteBuffer put(byte b) {
			return null;
		}

		@Override
		public byte get(int index) {
			return 0;
		}

		@Override
		public ByteBuffer put(int index, byte b) {
			return null;
		}

		@Override
		public ByteBuffer compact() {
			return null;
		}

		@Override
		public boolean isReadOnly() {
			return false;
		}

		@Override
		public boolean isDirect() {
			return false;
		}

		@Override
		public char getChar() {
			return 0;
		}

		@Override
		public ByteBuffer putChar(char value) {
			return null;
		}

		@Override
		public char getChar(int index) {
			return 0;
		}

		@Override
		public ByteBuffer putChar(int index, char value) {
			return null;
		}

		@Override
		public CharBuffer asCharBuffer() {
			return null;
		}

		@Override
		public short getShort() {
			return 0;
		}

		@Override
		public ByteBuffer putShort(short value) {
			return null;
		}

		@Override
		public short getShort(int index) {
			return 0;
		}

		@Override
		public ByteBuffer putShort(int index, short value) {
			return null;
		}

		@Override
		public ShortBuffer asShortBuffer() {
			return null;
		}

		@Override
		public int getInt() {
			return 0;
		}

		@Override
		public ByteBuffer putInt(int value) {
			return null;
		}

		@Override
		public int getInt(int index) {
			return 0;
		}

		@Override
		public ByteBuffer putInt(int index, int value) {
			return null;
		}

		@Override
		public IntBuffer asIntBuffer() {
			return null;
		}

		@Override
		public long getLong() {
			return 0;
		}

		@Override
		public ByteBuffer putLong(long value) {
			return null;
		}

		@Override
		public long getLong(int index) {
			return 0;
		}

		@Override
		public ByteBuffer putLong(int index, long value) {
			return null;
		}

		@Override
		public LongBuffer asLongBuffer() {
			return null;
		}

		@Override
		public float getFloat() {
			return 0;
		}

		@Override
		public ByteBuffer putFloat(float value) {
			return null;
		}

		@Override
		public float getFloat(int index) {
			return 0;
		}

		@Override
		public ByteBuffer putFloat(int index, float value) {
			return null;
		}

		@Override
		public FloatBuffer asFloatBuffer() {
			return null;
		}

		@Override
		public double getDouble() {
			return 0;
		}

		@Override
		public ByteBuffer putDouble(double value) {
			return null;
		}

		@Override
		public double getDouble(int index) {
			return 0;
		}

		@Override
		public ByteBuffer putDouble(int index, double value) {
			return null;
		}

		@Override
		public DoubleBuffer asDoubleBuffer() {
			return null;
		}
	}

	@Override
	public FileLock lock(long position, long size, boolean shared) throws IOException {
		throw new NonWritableChannelException();
	}

	@Override
	public FileLock tryLock(long position, long size, boolean shared) throws IOException {
		throw new NonWritableChannelException();
	}

	@Override
	protected void implCloseChannel() throws IOException {
		// No-op
	}
}
