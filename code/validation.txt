module TimeLogger
  module Validation

    def blank_space?(user_input)
      not user_input !~ /^\s*$/ 
    end
  end
end

