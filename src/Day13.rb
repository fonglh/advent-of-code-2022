# Follow sort comparator return values
# left < right, return -1
# left > right, return 1
# left == right, return 0
def correct_order(left_packet, right_packet)
  if left_packet.is_a? Integer and right_packet.is_a? Integer
    return left_packet <=> right_packet
  end

  if left_packet.is_a? Array and right_packet.is_a? Integer
    return correct_order(left_packet, [right_packet])
  elsif left_packet.is_a? Integer and right_packet.is_a? Array
    return correct_order([left_packet], right_packet)
  end

  # both are arrays
  for i in 0...left_packet.length do
    left_value = left_packet[i]
    if i < right_packet.length
      right_value = right_packet[i]
      ret_value = correct_order(left_value, right_value)
      if ret_value == 0
        next
      else
        return ret_value
      end
    else
      # right packet out of items
      return 1
    end
  end

  # left packet out of items, check if right packet still has stuff
  return left_packet.length == right_packet.length ? 0 : -1
end

file_data = File.open("Day13.txt").read.split("\n")

# Part 1
indexSum = 0

(0...file_data.length-1).step(3).each do |i|
  left_packet = eval file_data[i]
  right_packet = eval file_data[i+1]
  if correct_order(left_packet, right_packet) == -1
    # Every 3 lines is 1 pair, pairs start counting from 1
    indexSum += (i/3+1)
  end
end

puts indexSum

# Part 2
file_data.reject! { |line| line.empty? }
file_data.append("[[2]]")
file_data.append("[[6]]")
packets = []
file_data.each do |line|
  packets.append(eval line)
end

packets.sort! { |a, b| correct_order(a, b) }

divider1 = packets.index([[2]]) + 1
divider2 = packets.index([[6]]) + 1
puts divider1, divider2
puts divider1*divider2
